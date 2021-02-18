/*
 * Copyright (C) 2018 bsyonline
 */
package com.rolex.tips.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author rolex
 * @since 2018
 */
@Repository
@Slf4j
public class RedisDao {

    @Autowired
    RedisTemplate<String, byte[]> redisTemplate;
    private RuntimeSchema schema = null;

    public <T> T get(String id, Class<T> clazz) {
        schema = RuntimeSchema.createFrom(clazz);
        ValueOperations<String, byte[]> ops = redisTemplate.opsForValue();
        byte[] bytes = ops.get(id);
        if (bytes != null) {
            log.info("query redis hits: key={}", id);
            T t = (T) schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, t, schema);
            return t;
        }
        return null;
    }

    public void put(String key, Object obj) {
        byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        ValueOperations<String, byte[]> ops = redisTemplate.opsForValue();
        int expire = 60 * 60 * (new Random().nextInt(10) + 1);
        ops.set(key, bytes, expire, TimeUnit.SECONDS);
    }

}
