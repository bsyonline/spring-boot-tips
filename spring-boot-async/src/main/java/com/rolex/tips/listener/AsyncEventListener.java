package com.rolex.tips.listener;

import com.rolex.tips.event.AsyncEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
@Slf4j
public class AsyncEventListener implements ApplicationListener<AsyncEvent> {
    @SneakyThrows
    @Override
    @Async
    public void onApplicationEvent(AsyncEvent asyncEvent) {
        log.info("async task start");
        Thread.sleep(5000);
        log.info("async task completed");
    }
}
