/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.api.AsyncDemoService;
import com.rolex.tips.api.CompletableFutureService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

/**
 * @author rolex
 * @since 2020
 */
@DubboService(version = "1.0.0")
public class CompletableFutureServiceImpl implements CompletableFutureService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public CompletableFuture<String> fooCompletableFuture() {
        System.out.println("fooCompletableFuture开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompletableFuture<String> fooCompletableFuture = CompletableFuture.completedFuture("fooCompletableFuture");
        System.out.println("fooCompletableFuture执行完成");
        return fooCompletableFuture;
    }

    @Override
    public CompletableFuture<String> barCompletableFuture() {
        System.out.println("barCompletableFuture开始执行");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompletableFuture<String> barCompletableFuture = CompletableFuture.completedFuture("barCompletableFuture");
        System.out.println("barCompletableFuture执行完成");
        return barCompletableFuture;
    }

}
