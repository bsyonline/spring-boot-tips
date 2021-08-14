/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election4;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author rolex
 * @since 2021
 */
public class Node2 {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework.start();

        election(curatorFramework);

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void election(CuratorFramework curatorFramework) throws Exception {
        try {
            curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL).forPath("/zk/leader");

            System.out.println("创建了leader节点，成为leader");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建leader节点失败，成为follower");
            NodeCache nodeCache = new NodeCache(curatorFramework, "/zk/leader");
            nodeCache.start();
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData currentData = nodeCache.getCurrentData();
                    if (currentData == null) {
                        System.out.println("leader节点被删除了");
                        election(curatorFramework);
                    } else {
                        String path = currentData.getPath();
                        String data = new String(currentData.getData());
                        System.out.println("watch --> 节点变更, path=" + path + ", data=" + data);
                    }
                }
            });
        }
    }
}
