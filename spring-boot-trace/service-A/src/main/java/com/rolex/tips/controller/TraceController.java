package com.rolex.tips.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
public class TraceController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/trace")
    public String trace() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8082/users", HttpMethod.GET, null, String.class);
        ResponseEntity<String> exchange1 = restTemplate.exchange("http://localhost:8083/orders", HttpMethod.GET, null, String.class);
        ResponseEntity<String> exchange2 = restTemplate.exchange("http://localhost:8084/products", HttpMethod.GET, null, String.class);
        return String.format("A(%s, %s) \n\t-> %s\n\t-> %s\n\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), exchange.getBody(), exchange1.getBody(), exchange2.getBody());
    }

}
