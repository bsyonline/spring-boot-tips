package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ListOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test01() {
        String key = "12:msg";
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.leftPush(key, "A");
        ops.leftPush(key, "B");
        ops.leftPush(key, "C");
        // lrange
        assertEquals("[C, B, A]", Arrays.toString(ops.range(key, 0, 2).toArray()));

        ops.set(key, 0, "a");
        ops.set(key, 1, "b");
        ops.set(key, 2, "c");
        assertEquals("[a, b, c]", Arrays.toString(ops.range(key, 0, 2).toArray()));

        ops.rightPush(key, "X");
        ops.rightPush(key, "Y");
        ops.rightPush(key, "Z");
        assertEquals("[a, b, c, X, Y, Z]", Arrays.toString(ops.range(key, 0, 5).toArray()));
        assertEquals("a", ops.leftPop(key));
        assertEquals("Z", ops.rightPop(key));
        assertEquals(4, ops.size(key));
        while (ops.size(key) > 0) {
            System.out.printf(ops.leftPop(key) + " ");
        }
        redisTemplate.delete(key);
    }

    @Test
    public void test02() {
        String key = "history";
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.leftPush(key, "select * from user where id=1");
        ops.leftPush(key, "select * from dept where dept_name='sales'");
        List<String> histories = ops.range(key, 0, 5);
        for (String s : histories) {
            System.out.println(s);
        }
        redisTemplate.delete(key);
    }

}