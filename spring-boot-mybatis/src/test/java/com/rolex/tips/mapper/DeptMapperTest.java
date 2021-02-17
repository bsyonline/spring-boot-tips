package com.rolex.tips.mapper;

import com.rolex.tips.entity.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeptMapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    public void findAll() {
        List<Department> departments = departmentMapper.findAll();
        System.out.println(departments);
    }

}
