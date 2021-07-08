/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author rolex
 * @since 2020
 */
@DubboService(version = "1.0.0",
//        register = false, // 不向注册中心注册
        delay = 500, // 延迟服务发现
        executes = 10,
        actives = 10,
//        registry = {"beijing, shanghai"},
        connections = 10
)
public class DemoServiceImpl implements DemoService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

    @Override
    public void register(String name) {

    }
}

