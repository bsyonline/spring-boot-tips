package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Set;


/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ZSetOpsTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test01Add() {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        String key1 = "music:month:1:week:1";
        ops.incrementScore(key1, "yesterday once more", 1);
        ops.incrementScore(key1, "yesterday once more", 1);
        ops.incrementScore(key1, "yesterday once more", 1);
        ops.incrementScore(key1, "yesterday once more", 1);
        ops.incrementScore(key1, "hotel california", 1);
        // 周排行
        Set<ZSetOperations.TypedTuple<String>> top10 = ops.rangeWithScores(key1, 0, 10);
        System.out.println("1月第1周排行：");
        top10.forEach(t -> System.out.println(t.getValue() + " " + t.getScore()));

        String key2 = "music:month:1:week:2";
        ops.incrementScore(key2, "hotel california", 1);
        ops.incrementScore(key2, "hotel california", 1);
        ops.incrementScore(key2, "yesterday once more", 1);
        ops.incrementScore(key2, "yesterday once more", 1);
        ops.incrementScore(key2, "hotel california", 1);
        System.out.println("1月第2周排行：");
        top10 = ops.rangeWithScores(key2, 0, 10);
        top10.forEach(t -> System.out.println(t.getValue() + " " + t.getScore()));

        String key3 = "music:month:1:week:3";
        ops.incrementScore(key3, "hotel california", 1);
        ops.incrementScore(key3, "hotel california", 1);
        ops.incrementScore(key3, "yesterday once more", 1);
        ops.incrementScore(key3, "hotel california", 1);
        ops.incrementScore(key3, "hotel california", 1);
        System.out.println("1月第3周排行：");
        top10 = ops.rangeWithScores(key3, 0, 10);
        top10.forEach(t -> System.out.println(t.getValue() + " " + t.getScore()));

        String key4 = "music:month:1:week:4";
        ops.incrementScore(key4, "hotel california", 1);
        ops.incrementScore(key4, "hotel california", 1);
        ops.incrementScore(key4, "yesterday once more", 1);
        ops.incrementScore(key4, "hotel california", 1);
        ops.incrementScore(key4, "hotel california", 1);
        System.out.println("1月第4周排行：");
        top10 = ops.rangeWithScores(key4, 0, 10);
        top10.forEach(t -> System.out.println(t.getValue() + " " + t.getScore()));

        String key5 = "music:month:1";
        ops.unionAndStore(key1, Arrays.asList(key2, key3, key4), key5);
        top10 = ops.reverseRangeWithScores(key5, 0, -1);
        System.out.println("1月排行：");
        top10.forEach(t -> System.out.println(t.getValue() + " " + t.getScore()));
        System.out.println("yesterday once more: rank is " + ops.rank(key5, "yesterday once more") + ", score is " + ops.score(key5, "yesterday once more"));
        System.out.println("hotel california: rank is " + ops.rank(key5, "hotel california") +", score is " + ops.score(key5, "hotel california"));
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);
        redisTemplate.delete(key3);
        redisTemplate.delete(key4);
        redisTemplate.delete(key5);
    }

}