/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.config;

import com.rolex.tips.log.ResponseTimeInterceptor;
import com.rolex.tips.log.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author rolex
 * @since 2020
 */
@Configuration
public class ControllerConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 同一次请求traceId相同
         * */
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**");
        /**
         * 打印请求路径、请求参数已经返回的参数等等
         * */
        registry.addInterceptor(new ResponseTimeInterceptor()).addPathPatterns("/**");
    }
}
