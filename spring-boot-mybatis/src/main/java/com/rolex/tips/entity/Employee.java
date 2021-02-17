/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rolex
 * @since 2020
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    Integer id;
    String name;
    Department department;
}
