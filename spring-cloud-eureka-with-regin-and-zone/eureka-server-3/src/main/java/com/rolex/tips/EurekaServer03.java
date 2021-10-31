/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author rolex
 * @since 2020
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer03 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer03.class, args);
    }
}
