/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rolex
 * @since 2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
    String name;
    Integer gender;
    String genderName;
}
