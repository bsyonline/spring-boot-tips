/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rolex.tips.bo.EmployeeBo;
import com.rolex.tips.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rolex
 * @since 2020-06-20
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    Page<EmployeeBo> customPage(@Param("page") Page page, @Param("employee") Employee employee);

    List<Employee> selectAll();
}
