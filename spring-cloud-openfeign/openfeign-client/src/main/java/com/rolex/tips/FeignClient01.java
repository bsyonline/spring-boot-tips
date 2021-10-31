/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.model.Order;
import com.rolex.tips.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

/**
 * @author rolex
 * @since 2020
 */
@EnableFeignClients
@RestController
@SpringBootApplication
public class FeignClient01 {
    public static void main(String[] args) {
        SpringApplication.run(FeignClient01.class, args);
    }

    @Autowired
    OrderService orderService;

    @GetMapping("/orders/{id}")
    public Order findById(@PathVariable("id") String id) {
        return orderService.findById(id);
    }
}
