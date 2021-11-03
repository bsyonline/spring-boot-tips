package com.rolex.tips;

import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
@RestController
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping("/users/get")
    public User get(){
        return userService.getUserById(2L);
    }

    @RequestMapping("/users/add")
    public String add(Long id, String name){
        userService.create(new User(id, name));
        return "success";
    }

    @RequestMapping("/users/del")
    public String del(Long id){
        userService.evictCache(id);
        return "success";
    }
}
