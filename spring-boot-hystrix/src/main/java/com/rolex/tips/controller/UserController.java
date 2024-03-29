/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.command.GetGenderCommand;
import com.rolex.tips.command.GetUserCommand;
import com.rolex.tips.command.GetUserListCommand;
import com.rolex.tips.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        GetUserCommand getUserCommand = new GetUserCommand(id);
        User user = getUserCommand.execute();
        GetGenderCommand getGenderCommand = new GetGenderCommand(user.getGender());
        String genderName = getGenderCommand.execute();
        user.setGenderName(genderName);
        return user;
    }

    @GetMapping("/users/batch")
    public List<User> getUser() {
        List<User> list = new ArrayList<>();
        Long[] ids = {1L, 2L, 3L};
        GetUserListCommand getUserListCommand = new GetUserListCommand(ids);
        Observable<User> observe = getUserListCommand.observe();
        observe.subscribe(new Observer<User>() { // 等到调用subscribe然后才会执行
            @Override
            public void onCompleted() {
                System.out.println("获取完了所有的商品数据");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(User user) {
                GetGenderCommand getGenderCommand = new GetGenderCommand(user.getGender());
                String genderName = getGenderCommand.execute();
                user.setGenderName(genderName);
                list.add(user);
            }
        });
        return list;
    }

    @GetMapping("/users/list")
    public List<User> getUsers(String ids) {
        List<User> list = new ArrayList<>();
        for (String id : ids.split(",")) {
            list.add(new User(Long.valueOf(id), "user" + id, 1, null));
        }
        return list;
    }

}
