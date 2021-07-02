/*
 * Copyright (C) 2021 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.mapper.UserMapper;
import com.rolex.tips.vo.UserVO;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rolex
 * @since 2021
 */
@Slf4j
@RestController
@SpringBootApplication
@MapperScan("com.rolex.tips.mapper") // 和@Mapper二选一
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    UserMapper userMapper;

    @GetMapping("/query")
    public List query(@RequestBody UserVO userVO){
        log.info("{}-{}", userVO.getStart(), userVO.getEnd());
        return userMapper.findByCondition(userVO);
    }

}