package com.rolex.redis.hearbeat;

import com.rolex.redis.model.RegistryInfo;
import com.rolex.redis.sub.SubService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class RegistryInfoCheckThread extends Thread {

    @Autowired
    SubService subService;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Map<Integer, RegistryInfo> registry = subService.getRegistry();
            for (Map.Entry<Integer, RegistryInfo> kv : registry.entrySet()) {
                Integer nodeId = kv.getKey();
                long timestamp = kv.getValue().getTimestamp();
                if (timestamp < System.currentTimeMillis() - 3000 * 2) {
                    registry.remove(nodeId);
                }
            }
            Thread.sleep(1000);
        }
    }
}
