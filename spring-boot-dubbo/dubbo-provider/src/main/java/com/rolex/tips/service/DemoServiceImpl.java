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
@DubboService(version = "1.0.0")
public class DemoServiceImpl implements DemoService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
