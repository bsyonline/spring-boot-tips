/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import com.rolex.tips.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author rolex
 * @since 2021
 */
@Service
public class CacheServiceImpl implements CacheService {
    private static final String CACHE_NAME = "local";

    @Override
    @CachePut(value = CACHE_NAME, key = "'key_' + #user.getId()")
    public User saveLocalCache(User user) {
        return user;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'key_'+#id")
    public User getLocalCache(Long id) {
        return new User(-1L, "none");
    }
}
