/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rolex
 * @since 2020
 */
@Controller
@RequestMapping("/sys/logs")
public class LogController {
    @GetMapping
    public String list() {
        return "sys/log";
    }
}
