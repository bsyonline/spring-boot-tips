/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.leader.LeaderLatch;
import com.rolex.tips.leader.LeaderLatchListener;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
@Slf4j
public class App {

    @Autowired
    private RedissonClient redissonClient;
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void start() {
//        LeaderLatch node = new LeaderLatch(name, "podManager", new LeaderLatchListener() {
//            @Override
//            public void assignedLeader(RedissonClient redissonClient) {
//                log.info("{} - i'm leader", name);
//            }
//
//            @Override
//            public void releasesLeader() {
//                log.info("{} - leader lost, revocation leader action", name);
//            }
//        }, redissonClient);
//        node.startAsync();
    }
}
