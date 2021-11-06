/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.rolex.tips.model.User;

/**
 * @author rolex
 * @since 2021
 */
public class GetUserCommand extends HystrixCommand<User> {

    private Long id;

    public GetUserCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService")) // group的名字
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetUserCommand")) // command的名字，不指定默认就是类名
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetUserPool")) // 线程池的名字
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(3) // 线程池coreSize，默认10
                        .withMaximumSize(30) // 线程池最大线程数
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withKeepAliveTimeMinutes(1)
                        .withMaxQueueSize(15) //线程池的最大排队数量
                        .withQueueSizeRejectionThreshold(10))); // 当排队的数量超过阈值则reject
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        // remote call
        return new User(1L, "John", 1, null);
    }
}
