/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.rolex.tips.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@Mapper
public interface EmployeeMapper {

    List<Employee> findAll();

}
