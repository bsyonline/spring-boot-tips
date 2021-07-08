/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.tips.controller.vo.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理类
 *
 * @author rolex
 * @since 2021
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String LOGIN_RETURN_TYPE = "json";
    @Value("${spring.security.login-return-type}")
    private String returnType;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (LOGIN_RETURN_TYPE.equals(returnType)) {
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Response.success()));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
