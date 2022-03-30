package com.rolex.redis.hearbeat;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class HeartbeatThread extends Thread {
    @Resource
    private HeartbeatService heartbeatService;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            heartbeatService.heartbeat();
            Thread.sleep(3000);
        }
    }
}
