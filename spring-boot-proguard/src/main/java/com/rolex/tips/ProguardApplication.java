/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.proguard.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rolex
 * @since 2018
 */
@SpringBootApplication
public class ProguardApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ProguardApplication.class, args);
    }

    @Autowired
    Hello hello;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(hello.hello());
    }
}
