/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
@TableName("t_user_role")
public class UserRole {
    Long userId;
    Long roleId;
}
