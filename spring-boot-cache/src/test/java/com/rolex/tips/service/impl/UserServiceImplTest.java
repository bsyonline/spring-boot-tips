/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void test01() {
        userService.add(new User(1, "John"));
    }

    @Test
    public void test02() {
        User user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void test03() {
        User user = userService.getUserById(1);
        System.out.println(user);
        user = userService.update(new User(1, "John01"));
        System.out.println(user);
        user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void test04() {
        User user = userService.getUserById(1);
        System.out.println(user);
        userService.delete(1);
        System.out.println(user);
        user = userService.getUserById(1);
        System.out.println(user);
    }
}
