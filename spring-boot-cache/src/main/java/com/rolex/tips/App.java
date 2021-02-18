/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
@EnableCaching
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
