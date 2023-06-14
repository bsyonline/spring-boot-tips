package com.rolex.tips.service;

import com.rolex.tips.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class UserService {
    @Autowired
    User user;

    public String getName() {
        return user.getName();
    }
}
