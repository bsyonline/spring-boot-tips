package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@CacheConfig(cacheNames = {"test-caffeine"})
@Slf4j
public class UserServiceImpl implements UserService {

    @Cacheable(key = "#id")
    @Override
    public User getUser(Long id) {
        log.info("Trying to get user for id {} ", id);
        return new User(id, "Tom");
    }

    @Override
    @Cacheable(keyGenerator = "userKeyGenerator")
    public User getUserById(Long id) {
        log.info("Trying to get user for id {} ", id);
        return new User(id, "John");
    }

    @Override
    @CacheEvict(key = "#id")
    public void evictCache(Long id) {
        log.info("Evict cache entries...");
    }

    @Override
    @CachePut(key = "#user.id")
    public User create(User user) {
        log.info("Creating user {}", user);
        return user;
    }

}
