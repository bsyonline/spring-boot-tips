/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2020
 */
@EnableFeignClients
@SpringBootApplication
@RestController
public class ApplicationClient01 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationClient01.class, args);
    }

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/test")
    public String test(){
        return applicationService.test();
    }
}
