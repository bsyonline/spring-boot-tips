/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election1;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author rolex
 * @since 2021
 */
public class SelectionTest {
    public static void main(String[] args) throws InterruptedException {

        AtomicLong idGenerator = new AtomicLong();
        AtomicInteger activeNodeCount = new AtomicInteger();
        while (true) {
            final String name;
            if (activeNodeCount.get() >= 10) {
                System.out.println("节点数达到上限, 5秒后重试");
                Thread.sleep(5000);
                continue;
            }
            activeNodeCount.incrementAndGet();
            name = "node" + idGenerator.getAndDecrement();
            System.out.println("节点数未到上限, 创建节点" + name);

            new Thread(() -> {
                Node node = new Node(name, curatorFramework());
                try {
                    node.selection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (node.getStatus() == Node.NodeStatus.Leader && new Random().nextInt(10) < 3) {
                        node.shutdown();
                        activeNodeCount.decrementAndGet();
                        break;
                    }
                }
            }).start();
        }
    }

    public static CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();
        return client;
    }
}
