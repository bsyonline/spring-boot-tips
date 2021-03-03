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
@TableName("t_role")
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    Long id;
    String code;
    String name;
}
