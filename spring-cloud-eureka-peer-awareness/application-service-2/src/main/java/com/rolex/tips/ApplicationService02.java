/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2020
 */
@RestController
@SpringBootApplication
public class ApplicationService02 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationService02.class, args);
    }

    @Value("${server.port}")
    Integer port;

    @GetMapping("/test")
    public String test() {
        return "Service Instance: application-service-02 , Port: " + port;
    }
}
