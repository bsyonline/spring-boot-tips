/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
public class UpdateUserCommand extends HystrixCommand<Boolean> {

    private Long id;

    public UpdateUserCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserService")) // group的名字
                .andCommandKey(HystrixCommandKey.Factory.asKey("UpdateUserCommand"))); // command的名字，不指定默认就是类名
        this.id = id;
    }

    @Override
    protected Boolean run() throws Exception {
        // remote call
        log.info("update user, id={}", id);
        flushCache(id);
        return true;
    }

    public void flushCache(Long id) {
        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("GetUserCommand"),
                HystrixConcurrencyStrategyDefault.getInstance()).clear("user_" + id);
    }
}
