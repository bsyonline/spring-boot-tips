/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.tips.controller.vo.Response;
import com.rolex.tips.service.UserService;
import com.rolex.tips.service.bo.AuthUser;
import com.rolex.tips.service.impl.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author rolex
 * @since 2021
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    UserService userService;
    JwtService jwtService;
    protected JwtLoginFilter(String defaultFilterProcessesUrl,
                             AuthenticationManager authenticationManager,
                             UserService userService,
                             JwtService jwtService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    protected JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        AuthUser user = new ObjectMapper().readValue(req.getInputStream(), AuthUser.class);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwt = jwtService.generateToken((AuthUser) authResult.getPrincipal());
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(Response.success(jwt)));
        out.flush();
        out.close();
    }

    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write("登录失败!");
        out.flush();
        out.close();
    }
}