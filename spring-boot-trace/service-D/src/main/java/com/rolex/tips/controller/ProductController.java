package com.rolex.tips.controller;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rolex.tips.LogInterceptor.SPAN_ID;
import static com.rolex.tips.LogInterceptor.TRACE_ID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class ProductController {
    @GetMapping("/products")
    public String list() {
        return String.format("D(%s, %s)", MDC.get(TRACE_ID), MDC.get(SPAN_ID));
    }
}
