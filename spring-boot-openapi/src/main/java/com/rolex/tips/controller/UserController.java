package com.rolex.tips.controller;

import com.rolex.tips.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/user/list")
    public List<User> list(){
        return new ArrayList<>();
    }

}
