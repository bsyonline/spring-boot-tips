/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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

    public User(String name, Integer age, Gender gender, Skill skill) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.skill = skill;
    }

}
