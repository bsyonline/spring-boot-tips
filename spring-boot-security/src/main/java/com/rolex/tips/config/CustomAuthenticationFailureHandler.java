/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.tips.controller.vo.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证失败处理类
 *
 * @author rolex
 * @since 2021
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String LOGIN_RETURN_TYPE = "json";
    @Value("${spring.security.login-return-type}")
    private String returnType;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (LOGIN_RETURN_TYPE.equals(returnType)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Response.failure(400, "用户名或密码错误")));
        }else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
