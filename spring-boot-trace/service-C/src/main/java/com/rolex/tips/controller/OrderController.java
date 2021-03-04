package com.rolex.tips.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/orders")
    public String list() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8084/products", HttpMethod.GET, null, String.class);
        return String.format(" C(%s, %s) \n\t\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), exchange.getBody());
    }

}
