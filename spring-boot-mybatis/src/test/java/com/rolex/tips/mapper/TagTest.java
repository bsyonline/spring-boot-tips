/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.rolex.tips.entity.Tag;
import com.rolex.tips.entity.User;
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
public class TagTest {

    @Autowired
    TagMapper tagMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    public void insert() {
    }

    @Test
    public void findAll() {
        List<Tag> roles = tagMapper.findAll();
        System.out.println(roles);
    }

    @Test
    public void findUser() {
        User user = userMapper.findById(112);
        System.out.println(user);
    }

}
