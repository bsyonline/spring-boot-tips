/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
@MapperScan("com.rolex.tips.mapper") // 和@Mapper二选一
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}