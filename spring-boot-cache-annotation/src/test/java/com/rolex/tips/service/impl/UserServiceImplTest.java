package com.rolex.tips.service.impl;

import com.rolex.tips.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void test01() {
        User result = userService.findById(1);
        Assertions.assertEquals(new User(1, "John", 19), result);
    }

    @Test
    void test02() {
        User result = userService.findByIdWithCache(1);
        Assertions.assertEquals(new User(1, "John", 19), result);
    }
}
