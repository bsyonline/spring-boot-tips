/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.api.ProductService;
import com.rolex.tips.api.UserService;
import com.rolex.tips.api.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rolex.tips.filter.TraceIdFilter.SPAN_ID;
import static com.rolex.tips.filter.TraceIdFilter.TRACE_ID;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootApplication
@Slf4j
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @DubboReference(version = "1.0.0")
    private UserService userService;
    @DubboReference(version = "1.0.0")
    private OrderService orderService;
    @DubboReference(version = "1.0.0")
    private ProductService productService;

    @RequestMapping("/trace")
    public String sayHello() {
        return  String.format("A(%s, %s) \n\t-> %s\n\t-> %s\n\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), userService.list(), orderService.list(), productService.list());
    }

}
