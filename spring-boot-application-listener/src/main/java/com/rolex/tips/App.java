package com.rolex.tips;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@SpringBootApplication
@Slf4j
public class App implements ApplicationListener<ContextRefreshedEvent>, InitializingBean {

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("context refreshed");
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.info("{}", LocalDateTime.now());
        }, 5, 10, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("after properties set");
    }

    @PostConstruct
    public void a() {
        log.info("post construct1");
    }

    @PostConstruct
    public void b() {
        log.info("post construct2");
    }

}
