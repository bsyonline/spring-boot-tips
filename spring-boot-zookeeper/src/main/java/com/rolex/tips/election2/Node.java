/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author rolex
 * @since 2021
 */
public class Node extends LeaderSelectorListenerAdapter implements Closeable {

    private final String nodeName;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();

    public Node(CuratorFramework client, String path, String nodeName) {
        this.nodeName = nodeName;
        this.leaderSelector = new LeaderSelector(client, path, this);
        leaderSelector.autoRequeue();
    }

    public String getNodeName() {
        return nodeName;
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

    public void start() {
        leaderSelector.start();
        System.out.println(getNodeName() + "启动");
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.println(nodeName + " is now the leader. Waiting " + waitSeconds + " seconds...");
        System.out.println(nodeName + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            System.err.println(nodeName + " was interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(nodeName + " relinquishing leadership.\n");
        }
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
        System.out.println(getNodeName() + "宕机了");
    }
}
