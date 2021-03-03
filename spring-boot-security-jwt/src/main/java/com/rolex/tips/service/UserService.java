package com.rolex.tips.service;

import com.rolex.tips.service.bo.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public interface UserService extends UserDetailsService {

    SysUser getUserByName(String username);

    List<SysUser> list();
}
