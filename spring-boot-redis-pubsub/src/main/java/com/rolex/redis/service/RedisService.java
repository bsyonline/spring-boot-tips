package com.rolex.redis.service;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface RedisService {
    void sendChannelMess(String channel, String message);
}
