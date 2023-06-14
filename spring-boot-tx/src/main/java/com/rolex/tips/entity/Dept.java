/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author rolex
 * @since 2020-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_dept")
public class Dept extends Model<Dept> {

    private static final long serialVersionUID=1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    private String deptName;


    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

}
