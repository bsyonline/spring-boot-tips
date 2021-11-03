/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dao.impl;

import com.rolex.tips.dao.UserDao;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public User insert(User user) {
        log.info("add user: {}", user);
        return user;
    }

    @Override
    public User findById(Integer id) {
        Map<Integer, User> map = new HashMap<>();
        map.put(1, new User(1, "John"));
        map.put(2, new User(2, "Alice"));
        log.info("query user: {}", map.get(id));
        return map.get(id);
    }

    @Override
    public User update(User user) {
        log.info("update user: {}", user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        log.info("delete user: {}", new User(id, ""));
    }
}
