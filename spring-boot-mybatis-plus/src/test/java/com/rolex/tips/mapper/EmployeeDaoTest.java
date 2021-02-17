/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rolex.tips.entity.Employee;
import com.rolex.tips.service.IEmployeeService;
import com.rolex.tips.service.bo.EmployeeBo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EmployeeDaoTest {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void insert() {
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee();
            employee.setName("user" + i);
            employee.setAge(i);
            employee.setDeptId(1259510564693286913L);
            employee.setEmail("user" + i + "@gmail.com");
            list.add(employee);
        }
        employeeService.saveBatch(list);
    }

    @Test
    public void page1() {
        Page<Employee> page = new Page(3, 10);
        Page page1 = employeeService.page(page);
        System.out.println(page1.getRecords());
    }

    @Test
    public void page2() {
        Page<Employee> page = new Page(3, 10);
        Employee employee = new Employee();
        employee.setName("user");
        employee.setAge(30);
        Page<EmployeeBo> employeeBoPage = employeeMapper.customPage(page, employee);
        System.out.println(employeeBoPage.getRecords());
    }

    /**
     * SELECT id,name,age,dept_id,email FROM t_employee
     */
    @Test
    public void query1() {
        List<Employee> employees = employeeMapper.selectList(null);
    }

    /**
     * SELECT id,name,age,dept_id,email FROM t_employee WHERE (name = ?)
     */
    @Test
    public void query2() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Adele");
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email FROM t_employee WHERE (name LIKE ?)
     */
    @Test
    public void query3() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "Ade");
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email FROM t_employee
     * WHERE (dept_id IN (select dept_id from t_dept where dept_name='Sales'))
     */
    @Test
    public void query4() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("dept_id", "select dept_id from t_dept where dept_name='Sales'");
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee WHERE (date_format(created_time,'%Y-%m-%d')=?)
     */
    @Test
    public void query5() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m-%d')={0}", "2020-06-20");
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee
     * WHERE
     * (name LIKE ?
     * AND
     * (age < ? OR email IS NOT NULL))
     */
    @Test
    public void query6() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "Ade")
                .and(wq ->
                        wq.lt("age", 40).or().isNotNull("email")
                );
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee
     * WHERE (name LIKE ? AND (age BETWEEN ? AND ? AND (email IS NOT NULL)))
     */
    @Test
    public void query7() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "Ade")
                .and(wq -> wq.between("age", 20, 40).and(wqq -> wqq.isNotNull("email")));
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee
     * WHERE
     * (name LIKE ?
     * AND
     * (age < ? OR email IS NOT NULL))
     */
    @Test
    public void query8() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "Ade")
                .nested(wq ->
                        wq.lt("age", 40).or().isNotNull("email")
                );
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id FROM t_employee WHERE (name = ?)
     */
    @Test
    public void query9() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Adele").select("id");
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email FROM t_employee WHERE (name = ?)
     */
    @Test
    public void query10() {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Adele")
                .select(Employee.class, e -> !e.getColumn().equals("created_time"));
        List<Employee> employees = employeeMapper.selectList(queryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee WHERE (name = ?)
     */
    @Test
    public void query11() {
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Employee::getName, "Adele");
        List<Employee> employees = employeeMapper.selectList(lambdaQueryWrapper);
        System.out.println(employees);
    }

    /**
     * SELECT id,name,age,dept_id,email,created_time FROM t_employee WHERE (name LIKE ? AND age >= ?)
     */
    @Test
    public void query12() {
        List<Employee> userList = new LambdaQueryChainWrapper<>(employeeMapper)
                .like(Employee::getName, "Adele").ge(Employee::getAge, 20).list();
        userList.forEach(System.out::println);
    }

    @Test
    public void query13() {
        List<Employee> employees = employeeMapper.selectAll();
    }

    @Test
    public void query14() {
        List<Employee> userList = new LambdaQueryChainWrapper<>(employeeMapper)
                .apply("name = email")
                .eq(Employee::getName, "Adele")
                .list();
        userList.forEach(System.out::println);
    }

    @Test
    public void query15() {
        Employee employee = new Employee();
        employee.setName("aaa");
        employeeMapper.insert(employee);
        System.out.println(employee.getId());
    }
}
