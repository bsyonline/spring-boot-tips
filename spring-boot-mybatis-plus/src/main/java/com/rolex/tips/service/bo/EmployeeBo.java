/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rolex
 * @since 2020
 */
@Setter
@Getter
public class EmployeeBo {

    private Long id;

    private String name;

    private Integer age;

    private String deptName;

    private String email;

}
