/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rolex
 * @since 2019
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
    private Gender gender;
    private Skill skill;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    public User(String name, Integer age, Gender gender, Skill skill, Date createTime, Date updateTime) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.skill = skill;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
