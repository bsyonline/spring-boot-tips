/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election3;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

/**
 * @author rolex
 * @since 2021
 */
public class Node {

    CuratorFramework client;
    String path;
    String name;

    public Node(CuratorFramework client, String path, String name) {
        this.client = client;
        this.path = path;
        this.name = name;
    }

    public void selection() throws Exception {
        LeaderLatch leaderLatch = new LeaderLatch(client, path);
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println(name + "成为leader");
            }

            @Override
            public void notLeader() {
                System.out.println(name + "不是leader");
            }
        });
        leaderLatch.start();
    }
}
