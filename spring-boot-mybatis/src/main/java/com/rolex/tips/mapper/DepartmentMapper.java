package com.rolex.tips.mapper;

import com.rolex.tips.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@Mapper
public interface DepartmentMapper {
    List<Department> findAll();
}
