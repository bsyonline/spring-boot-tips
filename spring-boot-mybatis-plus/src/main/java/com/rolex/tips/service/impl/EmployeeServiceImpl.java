package com.rolex.tips.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rolex.tips.entity.Employee;
import com.rolex.tips.mapper.EmployeeMapper;
import com.rolex.tips.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rolex
 * @since 2020-06-20
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
