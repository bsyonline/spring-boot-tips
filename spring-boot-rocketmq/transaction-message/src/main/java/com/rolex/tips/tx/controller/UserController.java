package com.rolex.tips.tx.controller;

import com.rolex.tips.tx.model.User;
import com.rolex.tips.tx.service.UserService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/reg")
    public boolean reg() throws MQClientException {
        User user = new User();
        user.setName("john" + new Random().nextInt(100));
        user.setAge(30);
        user.setGender(1);
        user.setSkill("java");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userService.reg(user);
    }

}
