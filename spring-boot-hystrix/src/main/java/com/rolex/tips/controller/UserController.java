/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.command.CollapserTestCommand;
import com.rolex.tips.command.GetGenderCommand;
import com.rolex.tips.command.GetUserCommand;
import com.rolex.tips.command.GetUserFailureCommand;
import com.rolex.tips.command.GetUserListCommand;
import com.rolex.tips.command.UpdateUserCommand;
import com.rolex.tips.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        GetUserCommand getUserCommand = new GetUserCommand(id);
        User user = getUserCommand.execute();
        GetGenderCommand getGenderCommand = new GetGenderCommand(user.getGender());
        String genderName = getGenderCommand.execute();
        user.setGenderName(genderName);
        return user;
    }

    @GetMapping("/users/fallback")
    public User fallback() {
        GetUserFailureCommand getUserFailureCommand = new GetUserFailureCommand(1L);
        User user = getUserFailureCommand.execute();
        GetGenderCommand getGenderCommand = new GetGenderCommand(user.getGender());
        String genderName = getGenderCommand.execute();
        user.setGenderName(genderName);
        return user;
    }

    @GetMapping("/users/batch")
    public List<User> batch(String ids) {
        // ids=1,1,3,1
        List<User> list = new ArrayList<>();
        for (String id : ids.split(",")) {
            GetUserCommand getUserCommand = new GetUserCommand(Long.valueOf(id));
            User user = getUserCommand.execute();
            list.add(user);
            if (Objects.equals(id, "3")) {
                UpdateUserCommand updateUserCommand = new UpdateUserCommand(1L);
                updateUserCommand.execute();
            }
        }
        return list;
    }

    @GetMapping("/users")
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

    @GetMapping("/collapser")
    public List<User> testCollapser(String ids) throws ExecutionException, InterruptedException {
        List<Future<User>> futures = new ArrayList<>();
        for (String id : ids.split(",")) {
            CollapserTestCommand command = new CollapserTestCommand(Long.valueOf(id));
            Future<User> queue = command.queue();
            futures.add(queue);
        }
        List<User> list = new ArrayList<>();
        for (Future<User> future : futures) {
            list.add(future.get());
        }
        return list;
    }
}
