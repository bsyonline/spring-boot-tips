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
    User getUser(Long id);

    User getUser1(Long id);

    void evictCache(Long id);

    void create(User user);
}
