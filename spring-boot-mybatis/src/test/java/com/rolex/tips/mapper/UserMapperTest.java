/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rolex.tips.entity.Gender;
import com.rolex.tips.entity.Skill;
import com.rolex.tips.entity.User;
import com.rolex.tips.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author rolex
 * @since 2020
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User("tom", 19, Gender.Male, Skill.Java, new Date(), new Date());
        int count = userMapper.save(user);
        System.out.println("insert record count : " + count);
    }

    @Test
    public void findAll() {
        List<User> users = userMapper.findAll();
        System.out.println(users);
    }

    @Test
    public void findByCondition() {
        UserVO user = new UserVO();
        user.setAge(20);
        user.setGender(Gender.Male);
        List<User> users = userMapper.findByCondition(user);
        System.out.println(users);
    }

    @Test
    public void findByAnyCondition() {
        UserVO user = new UserVO();
//        user.setAge(20);
        user.setGender(Gender.Male);
        List<User> users = userMapper.findByAnyCondition(user);
        System.out.println(users);
    }

    @Test
    public void update() {
        User user = new User();
        user.setId(2);
        user.setGender(Gender.Female);
        userMapper.update(user);
    }

    @Test
    public void bulkInsert() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User("user" + (i + 1), new Random().nextInt(20) + 10, Gender.nameOf(new Random().nextInt(2) + 1), Skill.Java, new Date(), new Date());
            list.add(user);
        }
        int count = userMapper.batchSave(list);
        System.out.println("insert records count : " + count);
    }

    @Test
    public void bulkQuery() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<User> users = userMapper.batchQuery(list);
        System.out.println(users);
    }

    @Test
    public void bulkDelete() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        int count = userMapper.batchDelete(list);
        System.out.println("delete records count : " + count);
    }

    @Test
    public void bulkUpdate() {
        User user = new User(3, "alice", 29, Gender.Female, Skill.Java, new Date(), new Date());
        User user1 = new User(4, "jim", 29, Gender.Male, Skill.CPP, new Date(), new Date());
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        userMapper.batchUpdate(list);
    }

    @Test
    public void testCache() {
        List<User> users = userMapper.findAll();
        System.out.println(users);
        List<User> users1 = userMapper.findAll();
        System.out.println(users1);
    }

    @Test
    public void listForPage() {
        PageHelper.startPage(200, 5);
        List<User> list = userMapper.listForPage();
        PageInfo page = new PageInfo(list);
        //测试PageInfo全部属性
        //PageInfo包含了非常全面的分页属性
//        assertEquals(2, page.getPageNum());
//        assertEquals(5, page.getPageSize());
//        assertEquals(6, page.getStartRow());
//        assertEquals(10, page.getEndRow());
//        assertEquals(100, page.getTotal());
//        assertEquals(20, page.getPages());
//        assertEquals(true, page.isHasPreviousPage());
//        assertEquals(true, page.isHasNextPage());
        System.out.println(page.getList());
    }

    @Test
    public void findByNameAndAge() {
        List<User> list = userMapper.findByNameAndAge("user9", 12);
        System.out.println(list);
    }

    @Test
    public void findByAge() {
        List<User> list = userMapper.findByAge(20);
        System.out.println(list);
    }

    @Test
    public void groupByColumn() {
        List<Map<Integer, Integer>> list = userMapper.groupByColumn("age");
        System.out.println(list);
    }

    @Test
    public void testQuery1() {
        List<User> list = userMapper.testQuery(3);
        System.out.println(list);
    }
}
