/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author rolex
 * @since 2020
 */
@FeignClient(name = "application-service")
@Service
public interface ApplicationService {
    @GetMapping(value = "/test")
    String test();
}
