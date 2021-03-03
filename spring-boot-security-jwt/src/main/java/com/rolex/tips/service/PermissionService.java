package com.rolex.tips.service;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rolex
 * @since 2020
 */
public interface PermissionService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
