/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2021
 */
public class GetGenderCommand extends HystrixCommand<String> {

    int gender;
    private static final Map<Integer, String> map = new HashMap<>();

    public GetGenderCommand(int gender) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetGenderGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetGenderCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) // 指定使用信号量进行资源隔离
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(3))); // 信号量最大值
        this.gender = gender;
    }

    static {
        map.put(1, "male");
        map.put(2, "female");
    }

    @Override
    protected String run() throws Exception {
        return map.get(gender);
    }
}
