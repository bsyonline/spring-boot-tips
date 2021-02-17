package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HashOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test01Add() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        // 存储对象
        String key = "user";
        ops.put(key, "name", "tom");
        ops.put(key, "age", "20");
        ops.put(key, "gender", "male");
        System.out.println(ops.get(key, "name"));
        Set keys = ops.keys(key);
        System.out.println(Arrays.toString(keys.toArray()));
        System.out.println(Arrays.toString(ops.multiGet(key, keys).toArray()));
        ops.delete(key, "gender", "age");
        redisTemplate.delete(key);
    }

    @Test
    public void cart() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String userId = "1001";
        String goodId = "18";
        String key = "cart:" + userId;
        // 添加商品
        Long incr = ops.increment(key, "18", 1L);
        Long incr1 = ops.increment(key, "19", 1L);
        Long incr2 = ops.increment(key, "20", 1L);
        System.out.println(incr);
        System.out.println(incr1);
        System.out.println(incr2);
        // 增加数量
        Long count1 = ops.increment(key, "18", 1L);
        System.out.println(count1);
        Long count = Long.parseLong((String) ops.get(key, "18"));
        System.out.println("id 18 商品数量：" + count);
        // 商品总数
        Long len = ops.size(key);
        System.out.println(len);
        // 删除商品
        // 所有商品总数
        Map<Object, Object> map = ops.entries(key);
        System.out.println(map);
        redisTemplate.delete(key);
    }

}