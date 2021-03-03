/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.controller.vo.Response;
import com.rolex.tips.service.UserService;
import com.rolex.tips.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @PostMapping(value = "/token")
    public Response createToken(@RequestBody Map<String, String> authenticationMap) throws Exception {
        String username = authenticationMap.get("username");
        String password = authenticationMap.get("password");
        final UserDetails userDetails = userService.loadUserByUsername(username);
        final String token = jwtService.generateToken(userDetails);
        return Response.success(token);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public Response refreshToken(@RequestHeader("${jwt.header}") String token) throws Exception {
        return Response.success(jwtService.refreshToken(token));
    }

    @GetMapping("/orders")
    public String list() {
        return "orders";
    }
}
