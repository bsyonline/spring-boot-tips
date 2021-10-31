package com.rolex.tips;/*
 * Copyright (C) 2020 bsyonline
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author rolex
 * @since 2020
 */
@EnableEurekaServer
@SpringBootApplication
public class StandaloneServer {
    public static void main(String[] args) {
        SpringApplication.run(StandaloneServer.class, args);
    }
}
