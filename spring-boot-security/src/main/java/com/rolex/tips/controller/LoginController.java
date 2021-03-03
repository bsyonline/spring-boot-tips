/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author rolex
 * @since 2021
 */
@Controller
public class LoginController {

    @GetMapping("/invalidSession")
    public String invalidSession(){
        return "invalidSession";
    }

    @GetMapping("/login.html")
    public String login(){
        return "login";
    }
}
