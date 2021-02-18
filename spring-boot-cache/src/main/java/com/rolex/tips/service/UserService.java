/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

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
    User add(User user);

    User getUserById(Integer id);

    User update(User user);

    void delete(Integer id);
}
