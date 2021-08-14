/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.election3;

import org.apache.curator.framework.CuratorFramework;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class Node2 {

    @Autowired
    CuratorFramework client;

    @Test
    public void test() throws Exception {
        Node node = new Node(client,"/zk/leader", "node2");
        node.selection();

        Thread.sleep(Integer.MAX_VALUE);
    }

}
