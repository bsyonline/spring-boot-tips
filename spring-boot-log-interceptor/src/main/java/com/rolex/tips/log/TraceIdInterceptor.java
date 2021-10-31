/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.log;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author rolex
 * @since 2020
 */
public class TraceIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = UUID.randomUUID().toString().replace("-","");
        MDC.put(CommonConfig.TRACE_ID, traceId);
        return true;
    }

}
