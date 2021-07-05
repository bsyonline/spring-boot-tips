/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.api.CacheService;
import com.rolex.tips.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author rolex
 * @since 2020
 */
@DubboService(version = "1.0.0")
public class CacheServiceImpl implements CacheService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        System.out.println(serviceName + " 被调用，name = " + name);
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

}
