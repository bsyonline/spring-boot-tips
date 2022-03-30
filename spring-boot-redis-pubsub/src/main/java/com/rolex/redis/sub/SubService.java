package com.rolex.redis.sub;

import com.rolex.redis.model.RegistryInfo;

import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface SubService {

    void receiveMessage(String message);
    Map<Integer, RegistryInfo> getRegistry();
}