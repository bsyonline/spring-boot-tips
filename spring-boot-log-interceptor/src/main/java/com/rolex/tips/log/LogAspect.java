/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.log;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
//    @Before("@annotation(com.rolex.microlabs.log.Log)")
    @Before("execution(* com.rolex.tips.service.impl.*.*(..))")
    public void point(JoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(CommonConfig.TRACE_ID, MDC.get(CommonConfig.TRACE_ID));
        map.put("className", pjp.getTarget().getClass().getName());
        map.put("methodName", method.getName());
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        Object[] values = pjp.getArgs();
        Map<String, Object> param = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            param.put(parameterNames[i], values[i]);
        }
        map.put("param", param);
        log.info("{}", new Gson().toJson(map));
    }
}
