package com.rolex.tips.config;

import com.rolex.tips.interceptor.TraceIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
public class TraceIdInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private TraceIdInterceptor traceIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor)
                .addPathPatterns("/**");
    }
}
