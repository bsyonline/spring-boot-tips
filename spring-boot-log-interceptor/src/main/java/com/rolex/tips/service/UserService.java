/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rolex.tips.model.User;

/**
 * @author rolex
 * @since 2020
 */
public interface UserService {

    String get(Long id);
    String update(User user);

}
