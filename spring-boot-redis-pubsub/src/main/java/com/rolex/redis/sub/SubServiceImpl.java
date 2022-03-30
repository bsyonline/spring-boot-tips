package com.rolex.redis.sub;

import com.alibaba.fastjson.JSONObject;
import com.rolex.redis.model.RegistryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
@Component
public class SubServiceImpl implements SubService {

    public static Map<Integer, RegistryInfo> registry = new ConcurrentHashMap<>();

    @Override
    public void receiveMessage(String message) {
        log.info("收到消息：{}", message);
        RegistryInfo registryInfo = JSONObject.parseObject(message, RegistryInfo.class);
        registry.put(registryInfo.getNodeId(), registryInfo);
        log.info("===============================registry info is {}", registry);
    }

    @Override
    public Map<Integer, RegistryInfo> getRegistry() {
        return registry;
    }
}