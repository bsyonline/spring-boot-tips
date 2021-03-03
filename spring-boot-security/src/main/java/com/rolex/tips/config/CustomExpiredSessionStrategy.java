/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.tips.controller.vo.Response;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author rolex
 * @since 2021
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 重定向到某个页面
//        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/sessionExpired");


        // 返回json数据
        event.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        event.getResponse().setCharacterEncoding("UTF-8");
        event.getResponse().getWriter().write(
                new ObjectMapper().writeValueAsString(
                        Response.failure(403, "会话超时或已经在另一台机器上登录"
                                + event.getSessionInformation().getLastRequest())
                )
        );
    }
}
