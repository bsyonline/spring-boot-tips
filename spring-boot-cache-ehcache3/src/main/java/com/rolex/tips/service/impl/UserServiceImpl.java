package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements UserService {
    @Cacheable(value = "test-ehcache3", key = "#id")
    @Override
    public User getUser(Long id) {
        log.info("Trying to get user for id {} ", id);
        return new User(id, "Tom");
    }

    @Override
    @Cacheable(value = "test-ehcache3", keyGenerator = "userKeyGenerator")
    public User getUser1(Long id) {
        log.info("Trying to get user for id {} ", id);
        return new User(id, "John");
    }

    @Override
    @CacheEvict(cacheNames = {"test-ehcache3"}, key = "#id")
    public void evictCache(Long id) {
        log.info("Evict cache entries...");
    }

    @Override
    @CachePut(cacheNames ="test-ehcache3", key = "#user.id")
    public void create(User user) {
        log.info("Creating user {}", user);
    }
}
