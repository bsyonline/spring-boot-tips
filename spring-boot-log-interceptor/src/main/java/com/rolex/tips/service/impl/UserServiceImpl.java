/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service.impl;

import com.rolex.tips.log.Log;
import com.rolex.tips.model.User;
import com.rolex.tips.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rolex
 * @since 2020
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserService userService;

    @Override
    @Log
    public String get(Long id){
        System.out.println("service: user get");
        Set<Long> ids = new HashSet<>();
        ids.add(1000L);
        ids.add(1001L);
        ids.add(1002L);
        findAll(ids);
        /*
            调用相同的类中的方法时，直接调用不会触发AOP日志，会导致日志链路缺失
         */
        update(new User());
        /*
            有两种解决方案：
            1.使用AopContext.currentProxy()，需要在启动类启用@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
         */
        ((UserService) AopContext.currentProxy()).update(new User());
        /*
            2.@Autowired自己
         */
        userService.update(new User());
        return "OK";
    }

    @Override
    @Log
    public String update(User user){
        System.out.println("service: user update");
        return "OK";
    }

    @Log
    private Set<Long> findAll(Set<Long> ids){
        return new HashSet<>();
    }

}
