/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.entity;

import lombok.Data;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@Data
public class Department {
    Integer deptId;
    String deptName;
    List<Employee> employees;
}
