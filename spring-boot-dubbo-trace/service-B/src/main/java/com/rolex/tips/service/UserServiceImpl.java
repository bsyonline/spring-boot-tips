/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.api.RewardService;
import com.rolex.tips.api.StockService;
import com.rolex.tips.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.MDC;

import static com.rolex.tips.filter.TraceIdFilter.SPAN_ID;
import static com.rolex.tips.filter.TraceIdFilter.TRACE_ID;


/**
 * @author rolex
 * @since 2020
 */
@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @DubboReference(version = "1.0.0")
    StockService stockService;
    @DubboReference(version = "1.0.0")
    RewardService rewardService;
    @Override
    public String list() {
        return String.format(" B(%s, %s) \n\t\t-> %s\n\t\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), stockService.list(), rewardService.list());
    }
}
