/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips.controller;

import com.rolex.tips.model.User;
import com.rolex.tips.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @since 2021
 */
@RestController
public class CacheController {

    @Autowired
    CacheService cacheService;

    @RequestMapping("/putCache")
    public String putCache(User user){
        cacheService.saveLocalCache(user);
        return "success";
    }

    @GetMapping("/getCache")
    public User getCache(Long id){
        return cacheService.getLocalCache(id);
    }
}
