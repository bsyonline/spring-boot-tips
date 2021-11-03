/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.impl;

import com.rolex.tips.dao.UserDao;
import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @CachePut(key = "#user.id")
    @Override
    public User add(User user) {
        return userDao.insert(user);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id);
    }

    @CachePut(key = "#user.id")
    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @CacheEvict(key = "#id")
    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }
}
