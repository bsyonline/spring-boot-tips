/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedissonTest {
    public static final String KEY = "LOCK_KEY";

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test01() {
        String key = "key:01";
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set("abc");
        String obj = (String) bucket.get();
        System.out.println(obj);
        assertEquals("abc", obj);
        bucket.delete();
        String key2 = "key:02";
        RSet<Object> set = redissonClient.getSet(key2);
        set.add("a");
        set.add("a");
        set.add("a");
        Set<Object> set1 = set.readAll();
        assertEquals("[a]", Arrays.toString(set1.toArray()));
        set.delete();
    }

    @Test
    public void test02() {
        new Thread(() -> {
            RLock lock = redissonClient.getLock(KEY);
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "t1").start();
        new Thread(() -> {
            RLock lock = redissonClient.getLock(KEY);
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "t2").start();


        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
