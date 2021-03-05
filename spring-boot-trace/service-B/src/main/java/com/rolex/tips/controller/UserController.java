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
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/users")
    public String list() {
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8085/stocks", HttpMethod.GET, null, String.class);
        ResponseEntity<String> exchange1 = restTemplate.exchange("http://localhost:8086/rewards", HttpMethod.GET, null, String.class);
        return String.format(" B(%s, %s) \n\t\t-> %s\n\t\t-> %s", MDC.get(TRACE_ID), MDC.get(SPAN_ID), exchange.getBody(), exchange1.getBody());
    }

}
