package com.rolex.tips.leader;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractScheduledService;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonLock;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class LeaderLatch implements Closeable, MessageListener<String> {

    final String name;
    private final RedissonClient redissonClient;
    private final ExecutorService executorService;
    /**
     * leader选举的唯一标识
     */
    private final String leaderKey;
    private final LeaderLatchListener listener;
    /**
     * 锁
     */
    private final RedissonLock rLock;
    /**
     * 发布订阅
     */
    private final RTopic rTopic;
    /**
     * session管理
     */
    private final RMap<String, Session> sessionsWithTimeout;
    private final AtomicReference<State> state = new AtomicReference<State>(State.LATENT);
    /**
     * 持有锁的线程ID，用来组装lockkey
     */
    private final AtomicLong lockThreadId = new AtomicLong();
    private final Ticker ticker;

    private static long DEFAULT_TIMEOUT = 60 * 1000;
    private Long timeOut;
    private volatile boolean hasLeadership;

    /**
     *
     * @param leaderKey leader选举服务唯一标识
     * @param listener leader状态监听
     * @param redissonClient redission实例
     */
    public LeaderLatch(String name, String leaderKey, LeaderLatchListener listener, RedissonClient redissonClient) {
        this(name, leaderKey, listener, redissonClient, DEFAULT_TIMEOUT);
    }

    /**
     *
     * @param leaderKey leader选举服务唯一标识
     * @param listener leader状态监听
     * @param redissonClient redission实例
     * @param timeOut 心跳丢失时间阀值，单位毫秒
     */
    public LeaderLatch(String name, String leaderKey, LeaderLatchListener listener, RedissonClient redissonClient, Long timeOut) {
        this.name = name;
        this.leaderKey = leaderKey;
        this.redissonClient = redissonClient;
        log.info("id of this Redisson instance {}", redissonClient.getId());
        this.executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory("LeaderLatch-work"));
        this.rLock = (RedissonLock) redissonClient.getLock("LeaderLatch_LOCK_" + leaderKey);
        this.listener = new WrappedListener(this, listener);
        this.rTopic = redissonClient.getTopic("LeaderLatch_PUB_SUB_" + leaderKey);
        this.sessionsWithTimeout = redissonClient.getMap("LeaderLatch_SESSION_" + leaderKey);
        this.timeOut = timeOut;
        rTopic.addListener(String.class, this);
        ticker = new Ticker(this);

        Runtime.getRuntime().addShutdownHook(new Thread("LeaderLatch-shutdownhook") {
            @Override
            public void run() {
                try {
                    close();
                } catch (Exception e) {
                    log.error("leaderLatch close throw error", e);
                }
            }
        });
    }

    @Override
    public void close() throws IOException {
        log.info("LeaderLatch close...");
        Preconditions.checkState(state.compareAndSet(State.STARTED, State.CLOSED), "Already closed or has not been started");
        if (rLock.isHeldByThread(lockThreadId.get())) {
            rLock.unlock();
            listener.releasesLeader();
        }
        rTopic.removeListener(this);
        hasLeadership = false;
        ticker.stopAsync();
    }

    @Override
    public void onMessage(CharSequence channel, String msg) {
        try {
            log.info("leader exchange, lock status {}, hasLeadership {}, getLockName {}", rLock.isLocked(), hasLeadership, getLockName());
            //锁已释放，但是还处于leader状态，则主动释放leader
            if (!rLock.isHeldByThread(lockThreadId.get()) && hasLeadership) {
                log.info("This instance hasLeadership but not get the locked, releasesLeader");
                listener.releasesLeader();
            }
        } catch (Exception e) {
            log.error("onMessage throw error", e);
        }
    }

    /**
     * Attempt leadership. This attempt is done in the background - i.e. this method returns
     * immediately.<br><br>
     */
    public void startAsync() {
        log.info("LeaderLatch startAsync...");
        Preconditions.checkState(state.compareAndSet(State.LATENT, State.STARTED), "Cannot be started more than once");

        Future<Void> task = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    doWorkLoop();
                } finally {
                }
                return null;
            }
        });
        ticker.startAsync();
    }

    protected String getLockName() {
        return redissonClient.getId() + ":" + lockThreadId.get();
    }

    /**
     * LeaderLatchListener包装类
     * 获取leader后发出一个订阅消息，收到消息的instance需要校验leadership状态合法性
     */
    private class WrappedListener implements LeaderLatchListener {
        private final LeaderLatch leaderLatch;
        private final LeaderLatchListener listener;


        public WrappedListener(LeaderLatch leaderLatch, LeaderLatchListener listener) {
            this.leaderLatch = leaderLatch;
            this.listener = listener;
        }

        @Override
        public void assignedLeader(RedissonClient redissonClient) {
            listener.assignedLeader(redissonClient);
            leaderLatch.rTopic.publish("");
            try {
                log.info("{} leader is {} ", leaderLatch.leaderKey, name);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        @Override
        public synchronized void releasesLeader() {
            listener.releasesLeader();
        }
    }

    private synchronized void doWorkLoop() {
        try {
            if (!rLock.isLocked()) {
                log.info("no anyone get the lock, cluster no leadership");
                doWork();
            }
            log.info("do work loop down");
        } catch (Exception e) {
            log.error("doWork throw error", e);
        }
    }

    void doWork() {
        if (state.get() == State.STARTED) {
            hasLeadership = false;
            rLock.lock();
            lockThreadId.set(Thread.currentThread().getId());
            log.info("{} get lock success", getLockName());
            hasLeadership = true;
            listener.assignedLeader(redissonClient);
        }
    }

    /**
     * 心跳器
     */
    private class Ticker extends AbstractScheduledService {
        private Long nextExpirationTime = 0L;
        private final LeaderLatch leaderLatch;

        public Ticker(LeaderLatch leaderLatch) {
            this.leaderLatch = leaderLatch;
        }

        @Override
        protected String serviceName() {
            return "Ticker-redisson";
        }

        @Override
        protected void runOneIteration() throws Exception {
            try {
                if (hasLeadership) {
                    String id = leaderLatch.redissonClient.getId();
                    Session session = sessionsWithTimeout.get(id);
                    if (session != null) {
                        log.info("session is not expired and update expire time");
                        session.setNextExpireTime(System.currentTimeMillis() + session.getTimeout());
                        sessionsWithTimeout.put(id, session);
                        nextExpirationTime = System.currentTimeMillis() + session.getTimeout();
                    } else {
                        log.info("session is null, create a new session");
                        sessionsWithTimeout.put(redissonClient.getId(), new Session(redissonClient.getId(), timeOut));
                    }
                    log.info("loss ticker {} ms, lock status {}, hasLeadership {}, nextExpirationTime {}", System.currentTimeMillis() - nextExpirationTime, rLock.isLocked(), leaderLatch.hasLeadership, nextExpirationTime);
                }
            } catch (Exception e) {
                log.error("ticker throw error", e);
                touchSession();
            } finally {
                doWorkLoop();
            }
        }

        /**
         * session过期判断
         */
        private void touchSession() {
            long lostTick = System.currentTimeMillis() - nextExpirationTime;
            log.info("touchSession loss ticker {} ms", lostTick);
            if (nextExpirationTime > 0 && lostTick > timeOut.longValue()) {
                if (leaderLatch.hasLeadership) {
                    leaderLatch.listener.releasesLeader();
                    leaderLatch.hasLeadership = false;
                }
                nextExpirationTime = System.currentTimeMillis();
            }
        }

        @Override
        protected Scheduler scheduler() {
            return Scheduler.newFixedDelaySchedule(1, 1, TimeUnit.SECONDS);
        }
    }

    public enum State {
        LATENT,
        STARTED,
        CLOSED
    }

    /**
     * session 模拟
     */
    private static class Session implements Serializable {
        private static final long serialVersionUID = 5802964260238888426L;
        final String sessionId;
        final Long timeout;
        private Long nextExpireTime;

        public Session(String sessionId, Long timeout) {
            this.sessionId = sessionId;
            this.timeout = timeout;
        }

        public String getSessionId() {
            return sessionId;
        }

        public Long getTimeout() {
            return timeout;
        }

        public Long getNextExpireTime() {
            return nextExpireTime;
        }

        public void setNextExpireTime(Long nextExpireTime) {
            this.nextExpireTime = nextExpireTime;
        }
    }
}
