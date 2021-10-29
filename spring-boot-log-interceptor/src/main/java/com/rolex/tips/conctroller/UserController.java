/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.conctroller;

import com.rolex.tips.log.Log;
import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rolex
 * @since 2020
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    @ResponseBody
    @Log
    public User get(@PathVariable("id") Long id) {
        System.out.println("user get");
        userService.get(id);
        return new User();
    }

    @PostMapping("/users")
    @ResponseBody
    @Log
    public User update(@RequestBody User user) {
        System.out.println("user update");
        userService.update(user);
        return user;
    }

}
