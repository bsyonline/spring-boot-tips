/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class SysPermission {
    private Long id;
    private String code;
    private String name;
    private String url;
    private Long parentId;
}
