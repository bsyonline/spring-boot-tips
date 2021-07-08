/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.handler;

import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
public class MySavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

//        // Use the DefaultSavedRequest URL
//        String targetUrl = savedRequest.getRedirectUrl();
//        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

        Map<String, Object> map = new HashMap<>();
        response.getWriter().write(new Gson().toJson(map));


    }
}
