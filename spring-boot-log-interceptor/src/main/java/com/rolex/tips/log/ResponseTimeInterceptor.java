/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.log;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class ResponseTimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        Gson json = new Gson();
        Map<String, String> param = new LinkedHashMap<>();
        param.put(CommonConfig.TRACE_ID, MDC.get(CommonConfig.TRACE_ID));
        param.put(CommonConfig.LOG_STATE, CommonConfig.START);
        request.setAttribute(CommonConfig.START_TIME, startTime);
        param.put(CommonConfig.URI, request.getRequestURI());
        param.put(CommonConfig.URL, request.getRequestURL().toString());
        String className = ((HandlerMethod) handler).getBeanType().getName();
        param.put("className", className);
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        param.put("methodName", methodName);
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> {
            param.put(key, request.getParameter(key));
        });

        log.info(json.toJson(param));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Gson json = new Gson();
        Map<String, Object> param = new LinkedHashMap<>();
        Long startTime = (Long) request.getAttribute(CommonConfig.START_TIME);
        param.put(CommonConfig.TRACE_ID, MDC.get(CommonConfig.TRACE_ID));
        param.put(CommonConfig.LOG_STATE, CommonConfig.END);
        param.put(CommonConfig.RESPONSE_TIME, System.currentTimeMillis() - startTime);
        log.info(json.toJson(param));
    }
}