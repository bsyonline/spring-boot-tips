/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.model.User;

/**
 * @author rolex
 * @since 2021
 */
public interface CacheService {

    User saveLocalCache(User user);

    User getLocalCache(Long id);
}
