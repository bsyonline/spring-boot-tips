package com.rolex.tips.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SetOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @BeforeEach
    public void init() {

    }

    @Test
    public void test01() {
        String key = "top10";
        String key1 = "my_favourite";
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add(key, "Yesterday Once More");
        ops.add(key, "Finesse out the Gang Way");
        ops.add(key, "Holy Water");
        ops.add(key, "BOP");
        ops.add(key, "Wellerman");
        ops.add(key, "Knock Knock");
        ops.add(key, "Foot Fungus");
        ops.add(key, "Disrespectful");
        ops.add(key, "Less Like Me");
        ops.add(key, "Cold As You");
        System.out.println(ops.members(key));
        ops.move(key, "Yesterday Once More", key1);
        ops.move(key, "Yesterday Once More", key1);
        ops.move(key, "Yesterday Once More", key1);
        System.out.println(ops.members(key1));
        redisTemplate.delete(key);
        redisTemplate.delete(key1);
    }

    @Test
    public void test02() {
        String key = "lottery";
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add(key, "John");
        ops.add(key, "Tom");
        ops.add(key, "Alice");
        ops.add(key, "Lucy");
        ops.add(key, "Rose");
        ops.add(key, "Bob");
        ops.add(key, "Pony");
        ops.add(key, "Jacky");
        ops.add(key, "Ewa");
        // 可重复抽奖
        String s = ops.randomMember(key);
        String s1 = ops.randomMember(key);
        String s2 = ops.randomMember(key);
        System.out.println(s + " " + s1 + " " + s2);
        // 移除抽奖
        String s4 = ops.pop(key);
        String s5 = ops.pop(key);
        String s6 = ops.pop(key);
        System.out.println(s4 + " " + s5 + " " + s6);
        redisTemplate.delete(key);
    }

    @Test
    public void test03() {
        String key = "like:101";
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add(key, "11"); // 点赞
        ops.remove(key, "11"); // 取消点赞
        ops.isMember(key, "11"); // 是否点过赞
        Set<String> members = ops.members(key); // 点赞列表
        int size = members.size();// 点赞数
        redisTemplate.delete(key);
    }

    @Test
    public void test04() {
        String key1 = "tom:friend";
        String key2 = "alice:friend";
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add(key1, "john", "lucy", "bob");
        ops.add(key2, "pony", "rose", "john");
        Set<String> intersect = ops.intersect(key1, key2);
        System.out.println("共同好友：" + intersect);
        Set<String> difference = ops.difference(key1, key2);
        System.out.println("tom还有哪些好友：" + difference);
        Set<String> difference1 = ops.difference(key2, key1);
        System.out.println("alice还有哪些好友：" + difference1);
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);
    }

}