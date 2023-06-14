/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.test;

import com.rolex.tips.controller.TestController;
import com.rolex.tips.service.TxService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TxTest {

    @Autowired
    TestController testController;

    @Test
    public void insert() {
        String zhangsan = testController.hello("zhangsan");
        assertEquals("hi, zhangsan", zhangsan);
    }

}
