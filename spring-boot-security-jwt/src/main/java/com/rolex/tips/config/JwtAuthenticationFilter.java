/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import com.rolex.tips.service.UserService;
import com.rolex.tips.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author rolex
 * @since 2021
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    String jwtHeader;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(jwtHeader);
        if (!StringUtils.isEmpty(jwtToken)) {
            String username = jwtService.getUsernameFromToken(jwtToken);
            if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtService.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
