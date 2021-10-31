/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.google.common.collect.Lists;
import com.rolex.tips.model.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@RestController
@SpringBootApplication
public class FeignServer02 {
    public static void main(String[] args) {
        SpringApplication.run(FeignServer02.class, args);
    }

    @GetMapping("/orders")
    public List<Order> list() {
        return Lists.newArrayList(new Order("1", LocalDateTime.now().toString(), "1"), new Order("2", LocalDateTime.now().toString(), "2"));
    }

    @PostMapping("/orders")
    public Order add(@RequestBody Order order) {
        return order;
    }

    @GetMapping("/orders/{id}")
    public Order get(@PathVariable String id) {
        return new Order(id, LocalDateTime.now().toString(), "1");
    }

    @GetMapping("/orders/types")
    public List<Order> getByType(@RequestParam("type") String type) {
        return Lists.newArrayList(new Order("1", LocalDateTime.now().toString(), "1"));
    }
}
