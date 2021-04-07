package com.rolex.tips.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
@Slf4j
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/users")
    public String list() {
        log.info("----");
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8082/orders", HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }
}
