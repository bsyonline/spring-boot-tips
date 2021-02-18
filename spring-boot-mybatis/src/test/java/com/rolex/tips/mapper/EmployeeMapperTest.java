/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.rolex.tips.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void findAll() {
        List<Employee> employees = employeeMapper.findAll();
        System.out.println(employees);
    }

}
