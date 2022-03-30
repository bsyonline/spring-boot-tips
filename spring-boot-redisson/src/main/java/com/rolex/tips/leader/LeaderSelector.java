package com.rolex.tips.leader;

import com.google.common.base.Preconditions;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class LeaderSelector implements Closeable, MessageListener<String> {

    final String name;
    private final RedissonClient redissonClient;
    private final ExecutorService executorService;
    /**
     * leader选举的唯一标识
     */
    private final String leaderKey;
    private final LeaderSelectorListener listener;
    /**
     * 锁
     */
    private final RedissonLock rLock;
    /**
     * 发布订阅
     */
    private final RTopic rTopic;
    private final AtomicReference<LeaderSelector.State> state = new AtomicReference<LeaderSelector.State>(LeaderSelector.State.LATENT);
    /**
     * 持有锁的线程ID，用来组装lockkey
     */
    private final AtomicLong lockThreadId = new AtomicLong();
    private final AtomicReference<Thread> awaitThread = new AtomicReference();

    private static long DEFAULT_TIMEOUT = 60 * 1000;
    private Long timeOut;
    private volatile boolean hasLeadership;
    private boolean isQueued = false;
    private final AtomicBoolean autoRequeue = new AtomicBoolean(false);
    private final AtomicReference<Future<?>> ourTask = new AtomicReference<Future<?>>(null);
    private ConcurrentLinkedQueue<Thread> awaitQueue = new ConcurrentLinkedQueue();

    /**
     * @param leaderKey      leader选举服务唯一标识
     * @param listener       leader状态监听
     * @param redissonClient redission实例
     */
    public LeaderSelector(String name, String leaderKey, LeaderSelectorListener listener, RedissonClient redissonClient) {
        this(name, leaderKey, listener, redissonClient, DEFAULT_TIMEOUT);
    }

    /**
     * @param leaderKey      leader选举服务唯一标识
     * @param listener       leader状态监听
     * @param redissonClient redission实例
     * @param timeOut        心跳丢失时间阀值，单位毫秒
     */
    public LeaderSelector(String name, String leaderKey, LeaderSelectorListener listener, RedissonClient redissonClient, Long timeOut) {
        this.name = name;
        this.leaderKey = leaderKey;
        this.redissonClient = redissonClient;
        log.info("id of this Redisson instance {}", redissonClient.getId());
        this.executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory("LeaderSelector-worker"));
        this.rLock = (RedissonLock) redissonClient.getLock("LeaderSelector_LOCK_" + leaderKey);
        this.listener = new LeaderSelector.WrappedListener(this, listener);
        this.rTopic = redissonClient.getTopic("LeaderSelector_PUB_SUB_" + leaderKey);
        this.timeOut = timeOut;
        rTopic.addListener(String.class, this);

        Runtime.getRuntime().addShutdownHook(new Thread("LeaderSelector-shutdownhook") {
            @Override
            public void run() {
                try {
                    close();
                } catch (Exception e) {
                    log.error("leaderSelector close throw error", e);
                }
            }
        });
    }

    @Override
    public void close() throws IOException {
        Preconditions.checkState(state.compareAndSet(State.STARTED, State.CLOSED), "Already closed or has not been started");
        executorService.shutdown();
        ourTask.set(null);
    }

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info("on message:{}", msg);
        String[] split = msg.split(":");
        if ("release".equals(split[0])) {
            Thread poll = awaitQueue.poll();
            if (poll != null) {
                log.info("notify await thread:{}", poll.getId());
                LockSupport.unpark(poll);
            }
        } else {
            if(!Objects.equals(name, split[1]) && hasLeadership){
                log.info("{} release self leadership", getLockName());
            }
        }
    }

    public void start() {
        boolean b = state.compareAndSet(State.LATENT, State.STARTED);
        if (state.get() != State.STARTED || internalRequeue()) {
            log.info("{} 抢锁失败，await", getLockName());
            awaitThread.set(Thread.currentThread());
            awaitQueue.add(Thread.currentThread());
            LockSupport.park();
        }
        start();
    }

    private synchronized boolean internalRequeue() {
        if (!isQueued && (state.get() == State.STARTED)) {
            log.info("{} internal requeue", getLockName());
            isQueued = true;
            Future<Void> task = executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        doWorkLoop();
                    } finally {
                        log.info("{} internal requeue quit", getLockName());
                        clearIsQueued();
                        if (autoRequeue.get()) {
                            log.info("{} auto requeue", getLockName());
                            internalRequeue();
                        }
                    }
                    return null;
                }
            });
            ourTask.set(task);

            return true;
        }
        return false;
    }

    private synchronized void clearIsQueued() {
        isQueued = false;
    }

    private synchronized void doWorkLoop() {
        try {
            doWork();
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw e;
        }
    }

    void doWork() throws InterruptedException {
        try {
            if (state.get() == LeaderSelector.State.STARTED) {
                hasLeadership = false;
                try {
                    rLock.lock();
                    lockThreadId.set(Thread.currentThread().getId());
                    awaitThread.set(Thread.currentThread());
                    log.info("{} get lock success", getLockName());
                    log.info("{} leadership changed, {} -> {}", getLockName(), false, true);
                    hasLeadership = true;
                    listener.takeLeadership(redissonClient);
                } catch (Exception e) {
                    throw e;
                } finally {
                    log.info("{} do work finally", getLockName());
                    clearIsQueued();
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw e;
        } finally {
            if (hasLeadership) {
                hasLeadership = false;
                log.info("{} leadership changed, {} -> {}", getLockName(), true, false);
                try {
                    rLock.unlock();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    log.error("The leader threw an exception", e);
                }
                log.info("{} 广播leader退出", getLockName());
                rTopic.publish("release:" + getLockName());
            }
        }

    }

    protected String getLockName() {
//        return redissonClient.getId() + ":" + Thread.currentThread().getId();
        return name;
    }

    class WrappedListener implements LeaderSelectorListener {
        private final LeaderSelector leaderSelector;
        private final LeaderSelectorListener listener;


        public WrappedListener(LeaderSelector leaderSelector, LeaderSelectorListener listener) {
            this.leaderSelector = leaderSelector;
            this.listener = listener;
        }

        @Override
        public void takeLeadership(RedissonClient redissonClient) throws InterruptedException {
            try {
                log.info("{} 广播leader当选，{} leader is {} ", getLockName(), leaderSelector.leaderKey, name);
                leaderSelector.rTopic.publish("elected:" + getLockName());
                listener.takeLeadership(redissonClient);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        @Override
        public void stateChanged(RedissonClient redissonClient, State newState) {
            try {
                log.info("{} state changed", getLockName(), leaderSelector.leaderKey);
                listener.stateChanged(redissonClient, newState);
            } catch (Exception dummy) {
                leaderSelector.interruptLeadership();
            }
        }

    }

    public synchronized void interruptLeadership() {
        Future<?> task = ourTask.get();
        if (task != null) {
            task.cancel(true);
        }
    }

    public enum State {
        LATENT,
        STARTED,
        CLOSED
    }

}
