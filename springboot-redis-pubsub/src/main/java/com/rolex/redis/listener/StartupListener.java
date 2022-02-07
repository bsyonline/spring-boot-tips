package com.rolex.redis.listener;

import com.rolex.redis.hearbeat.HeartbeatThread;
import com.rolex.redis.hearbeat.RegistryInfoCheckThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private HeartbeatThread heartbeatThread;
    @Autowired
    private RegistryInfoCheckThread registryInfoCheckThread;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        heartbeatThread.start();
        registryInfoCheckThread.start();
    }
}
