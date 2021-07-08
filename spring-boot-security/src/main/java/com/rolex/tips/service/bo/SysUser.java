/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.bo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class SysUser {
    Integer id;
    String username;
    String password;
    Integer state;
    Integer accountExpired;
    Integer passwordExpired;
    Integer accountLocked;
    List<SysRole> roles;
}
