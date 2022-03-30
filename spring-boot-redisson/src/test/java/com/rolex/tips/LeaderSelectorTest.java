package com.rolex.tips;

import com.rolex.tips.leader.LeaderSelector;
import com.rolex.tips.leader.LeaderSelectorListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class LeaderSelectorTest {
    @Autowired
    private RedissonClient redissonClient;
    private static final int CLIENT_QTY = 10;

    private static final String PATH = "leader";

    @Test
    public void test() throws InterruptedException {
        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    countDownLatch.await();
                    final String name = "node" + Thread.currentThread().getId();
                    LeaderSelector node1 = new LeaderSelector(name, "TimeManger", new LeaderSelectorListener() {
                        @Override
                        public void takeLeadership(RedissonClient redissonClient) throws InterruptedException {
                            log.info("{} 拿到leader，开始处理", name);
                            Thread.sleep(5000);
                            log.info("{} 处理完成", name);
                        }

                        @Override
                        public void stateChanged(RedissonClient client, LeaderSelector.State newState) {
                            log.info("状态变成了：{}", newState);
                        }
                    }, redissonClient);
                    node1.start();
                }
            }).start();
            countDownLatch.countDown();
        }

        Thread.sleep(Integer.MAX_VALUE);

    }

    @Test
    public void test1() throws InterruptedException {

        final String name = "john";
        LeaderSelector node1 = new LeaderSelector(name, "TimeManger", new LeaderSelectorListener() {
            @Override
            public void takeLeadership(RedissonClient redissonClient) throws InterruptedException {
                log.info("{} 拿到leader，开始处理", name);
                Thread.sleep(1000);
                log.info("{}", name);
                Thread.sleep(1000);
                log.info("{}", name);
                Thread.sleep(1000);
                log.info("{}", name);
                Thread.sleep(1000);
                log.info("{}", name);
                Thread.sleep(1000);
                log.info("{}", name);
                log.info("{} 处理完成", name);
            }

            @Override
            public void stateChanged(RedissonClient client, LeaderSelector.State newState) {
                log.info("状态变成了：{}", newState);
            }
        }, redissonClient);
        node1.start();

        Thread.sleep(Integer.MAX_VALUE);
    }

}
