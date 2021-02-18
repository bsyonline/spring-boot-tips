/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dao;

import com.rolex.tips.model.User;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface UserDao {

    User insert(User user);

    User findById(Integer id);

    User update(User user);

    void delete(Integer id);

}
