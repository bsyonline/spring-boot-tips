/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.entity;

/**
 * @author rolex
 * @since 2020
 */
public enum Skill {
    None("None"),
    Java("Java"),
    CPP("CPP"),
    Python("Python");

    private String value;

    Skill(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Skill nameOf(String value) {
        for (Skill skill : Skill.values()) {
            if (skill.value.equals(value)) {
                return skill;
            }
        }
        return None;
    }
}
