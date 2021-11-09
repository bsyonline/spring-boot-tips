/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class CircuitBreakerTestCommand extends HystrixCommand<User> {

    private Long id;

    public CircuitBreakerTestCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService")) // group的名字
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetUserCommand")) // command的名字，不指定默认就是类名
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetUserPool")) // 线程池的名字
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(3) // 线程池coreSize，默认10
                        .withMaximumSize(30) // 线程池最大线程数
                        .withAllowMaximumSizeToDivergeFromCoreSize(true)
                        .withKeepAliveTimeMinutes(1)
                        .withMaxQueueSize(15) //线程池的最大排队数量
                        .withQueueSizeRejectionThreshold(10)) // 当排队的数量超过阈值则reject
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(10) // fallback的并发数
                        .withCircuitBreakerEnabled(true) // 是否开启短路器，默认是true
                        .withExecutionTimeoutInMilliseconds(1000) // 超时
                        .withMetricsRollingStatisticalWindowBuckets(10) // 统计的bucket数量，默认10秒10个bucket，即1秒1个bucket
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000) // 统计窗口时长，默认10秒
                        .withCircuitBreakerRequestVolumeThreshold(20) // 滑动窗口中有多少个请求才开始统计，默认20
                        .withCircuitBreakerSleepWindowInMilliseconds(5000) // 开启短路器后，经过多长时间短路器变成half-open，默认5s
                        .withCircuitBreakerErrorThresholdPercentage(50) // 经过短路器的异常请求比例，默认50
                )
        );
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        // remote call
        log.info("get user from remote service, id={}", id);
        if (id == -100) {
            throw new RuntimeException();
        }
        return new User(id, "user" + id, 1, null);
    }

    @Override
    protected User getFallback() {
        return new User(-1L, "降级用户", 1, null);
    }
}
