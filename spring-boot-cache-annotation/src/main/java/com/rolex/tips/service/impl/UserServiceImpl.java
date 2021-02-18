/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.cache.annotation.Cache;
import com.rolex.tips.dao.RedisDao;
import com.rolex.tips.dao.UserDao;
import com.rolex.tips.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rolex
 * @since 2019
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RedisDao redisDao;

    public User findById(int id) {
        User user = (User) redisDao.get(String.valueOf(id), User.class);
        if (user == null) {
            log.info("query redis not hits and get from db: key={}", id);
            user = userDao.findById(id);
            if (user != null) {
                redisDao.put(String.valueOf(user.getId()), user);
                log.info("put into redis: key={}", id);
            }
        }
        return user;
    }

    @Cache(key = "#id", returnType = User.class)
    public User findByIdWithCache(int id) {
        return userDao.findById(id);
    }
}
