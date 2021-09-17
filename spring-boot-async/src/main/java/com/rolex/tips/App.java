package com.rolex.tips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootApplication
@EnableAsync
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
