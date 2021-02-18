/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface UserService {
    User findById(int id);

    User findByIdWithCache(int id);
}
