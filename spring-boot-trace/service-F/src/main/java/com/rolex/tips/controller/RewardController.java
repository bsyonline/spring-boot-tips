package com.rolex.tips.controller;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rolex.tips.log.interceptor.LogInterceptor.SPAN_ID;
import static com.rolex.tips.log.interceptor.LogInterceptor.TRACE_ID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class RewardController {
    @GetMapping("/rewards")
    public String list() {
        return String.format("F(%s, %s)", MDC.get(TRACE_ID), MDC.get(SPAN_ID));
    }
}
