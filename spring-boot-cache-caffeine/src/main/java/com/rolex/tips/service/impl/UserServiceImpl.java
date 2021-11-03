package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = {"test-cache"})
@Slf4j
public class UserServiceImpl implements UserService {

    @Cacheable
    @Override
    public User getUser(Long id) {
        log.info("Trying to get user for id {} ", id);
        return new User(id, "Tom");
    }

}
