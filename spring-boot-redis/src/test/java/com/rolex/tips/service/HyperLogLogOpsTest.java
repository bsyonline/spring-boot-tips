package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HyperLogLogOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test01Add() {
        String key1 = "uv20200101";
        HyperLogLogOperations<String, String> ops = redisTemplate.opsForHyperLogLog();
        ops.add(key1, "1");
        ops.add(key1, "3");
        ops.add(key1, "2");
        ops.add(key1, "2");
        // 20200101 访问量
        System.out.println("20200101登录用户数:" + ops.size(key1));
        String key2 = "uv20200102";
        ops.add(key2, "1");
        ops.add(key2, "3");
        ops.add(key2, "2");
        ops.add(key2, "1");
        ops.add(key2, "4");
        // 20200101 访问量
        System.out.println("20200102登录用户数:" + ops.size(key2));
        String key3 = "uv";
        ops.union(key3, key1, key2);
        // 总访问量
        System.out.println("登录用户合计:" + ops.size(key3));
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);
        redisTemplate.delete(key3);
    }

}