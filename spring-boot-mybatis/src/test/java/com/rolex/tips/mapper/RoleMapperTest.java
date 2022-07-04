/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rolex.tips.entity.Gender;
import com.rolex.tips.entity.Role;
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
public class RoleMapperTest {

    @Autowired
    RoleMapper roleMapper;

    @Test
    public void insert() {
    }

    @Test
    public void findAll() {
        List<Role> roles = roleMapper.findAll();
        System.out.println(roles);
    }

}
