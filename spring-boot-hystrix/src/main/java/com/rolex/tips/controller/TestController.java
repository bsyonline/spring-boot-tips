/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.command.GetGenderCommand;
import com.rolex.tips.command.GetUserCommand;
import com.rolex.tips.command.UpdateUserCommand;
import com.rolex.tips.command.test.CollapserTestCommand;
import com.rolex.tips.command.test.FallbackTestCommand;
import com.rolex.tips.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TestController {

    @GetMapping("/test/fallback")
    public User fallback() {
        FallbackTestCommand fallbackTestCommand = new FallbackTestCommand(1L);
        User user = fallbackTestCommand.execute();
        GetGenderCommand getGenderCommand = new GetGenderCommand(user.getGender());
        String genderName = getGenderCommand.execute();
        user.setGenderName(genderName);
        return user;
    }

    @GetMapping("/test/cache")
    public List<User> cache(String ids) {
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

    @GetMapping("/test/collapser")
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
