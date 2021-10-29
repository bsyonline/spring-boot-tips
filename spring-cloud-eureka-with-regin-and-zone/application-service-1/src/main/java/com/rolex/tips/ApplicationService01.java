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
@SpringBootApplication
@RestController
public class ApplicationService01 {
    @Value("${eureka.instance.metadata-map.zone}")
    String zoneName;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationService01.class, args);
    }

    @GetMapping("/zone")
    public String getZoneName() {
        return zoneName;
    }
}
