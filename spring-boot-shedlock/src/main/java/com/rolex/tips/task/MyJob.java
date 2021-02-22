package com.rolex.tips.task;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
public class MyJob {
    @Value("${server.port}")
    private int port;

    @Scheduled(cron = "0/5 * * * * ?")
    @SchedulerLock(name = "myJob", lockAtMostFor = "PT4S", lockAtLeastFor = "PT4S")
    public void job() {
        log.info("myJob {}", port);
    }
}