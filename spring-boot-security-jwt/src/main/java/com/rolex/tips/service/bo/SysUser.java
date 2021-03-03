/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.bo;

import lombok.Getter;
import lombok.Setter;

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
