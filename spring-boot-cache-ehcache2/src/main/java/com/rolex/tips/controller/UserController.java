package com.rolex.tips.controller;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @RequestMapping("/users/add")
    public String save(Integer id, String name) {
        userService.add(new User(id, name));
        return "success";
    }

}
