/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
@TableName("t_permission")
public class Permission {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    private String url;
    private Long parentId;
}
