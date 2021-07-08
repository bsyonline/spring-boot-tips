/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.api.AsyncDemoService;
import com.rolex.tips.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

/**
 * @author rolex
 * @since 2020
 */
@DubboService(version = "1.0.0", async = true)
public class AsyncDemoServiceImpl implements AsyncDemoService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String foo(){
        System.out.println("foo开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("foo执行完成");
        return "foo";
    }

    @Override
    public String bar(){
        System.out.println("bar开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("bar执行完成");
        return "bar";
    }


}
