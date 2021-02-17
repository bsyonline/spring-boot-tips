package com.rolex.tips.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class StringOpsTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    public void init() {

    }

    @Test
    public void test01articleViewCount() {
        String key = "article:view:19";
        redisTemplate.opsForValue().set(key, "5");
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForValue().increment(key);
        String count = redisTemplate.opsForValue().get(key);
        System.out.println("count=" + count);
        assertEquals("8", count);
        redisTemplate.delete(key);
    }

    @Test
    public void test02() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 统计签到
        String key = "sign_tom";
        ops.setBit(key, 20200101, true);
        ops.setBit(key, 20200102, true);
        ops.setBit(key, 20200104, true);
        ops.setBit(key, 20200105, true);
        Long count = redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
        System.out.println(count);
        redisTemplate.delete(key);
    }

}