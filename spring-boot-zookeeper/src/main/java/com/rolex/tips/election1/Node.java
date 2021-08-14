/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election1;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;

/**
 * @author rolex
 * @since 2021
 */
public class Node {
    private NodeStatus status;
    private String name;

    public Node(String name, CuratorFramework client) {
        this.name = name;
        this.client = client;
    }

    CuratorFramework client;
    public static final String path = "/zk/leader";

    public NodeStatus getStatus() {
        return status;
    }

    public void selection() throws Exception {
        try {
            status = NodeStatus.Looking;
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            status = NodeStatus.Leader;
            System.out.println("------------------------------------" + name + "选为leader, status=" + status);
        } catch (Exception e) {
            status = NodeStatus.Follower;
            System.out.println("------------------------------------" + name + "成为follower, status=" + status);
            NodeCache nodeCache = new NodeCache(client, path);
            nodeCache.start();
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData currentData = nodeCache.getCurrentData();
                    if (currentData == null) {
                        selection();
                    }
                }
            });
        }
    }

    public void shutdown() {
        client.close();
        System.out.println("------------------------------------" + name + "宕机");
    }

    enum NodeStatus {
        Looking,
        Leader,
        Follower;
    }

}
