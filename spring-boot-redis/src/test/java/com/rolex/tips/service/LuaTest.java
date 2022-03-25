package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LuaTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test01() {
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/test.lua")));
        List<String> keys = Arrays.asList("aaa");
        // 10秒内小于或等于3次时返回1，否则返回0
        for (int i = 0; i < 4; i++) {
            Object execute = redisTemplate.execute(defaultRedisScript, keys, 10, 3);
            System.out.println(execute);
        }
    }

    @Test
    public void test02() {
        /*
            ZADD admin_job_priority 5 shell_1
            ZADD admin_job_priority 2 shell_2
            ZADD admin_job_priority 1 spark_3
            ZADD admin_job_priority 5 spark_4
            ZADD admin_job_priority 3 shell_8

            zrevrange admin_job_priority 0 -1 WITHSCORES

            ZREM admin_job_priority shell_1
         */
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(List.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/test1.lua")));
        List<String> keys = Arrays.asList("admin_job_priority");
        Object[] params = new Object[]{
                0, //from
                -1, //to
                "shell" //type
        };

        Object execute = redisTemplate.execute(defaultRedisScript, keys, params);
        System.out.println(execute);
    }
}
