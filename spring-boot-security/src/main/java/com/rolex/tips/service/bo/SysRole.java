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
public class SysRole {
    Long id;
    String code;
    String name;
    List<SysPermission> permissions;
}
