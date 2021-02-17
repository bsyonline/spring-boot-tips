package com.rolex.tips.entity;

/**
 * @author rolex
 * @since 2020
 */
public enum Gender {
    Male(1),
    Female(2);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static Gender nameOf(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        return null;
    }
}
