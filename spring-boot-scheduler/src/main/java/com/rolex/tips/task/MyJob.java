package com.rolex.tips.task;

import lombok.extern.slf4j.Slf4j;
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
@Component
@Slf4j
public class MyJob {

    @Scheduled(cron = "0/2 * * * * ?")
    public void show() {
        log.info("每2秒执行一次");
    }

    @Scheduled(fixedDelay = 1000)
    public void fixedDelayScheduled() {
        log.info("上一次调度成功后，隔1秒再执行");
    }

    @Scheduled(fixedRate = 1000)
    public void fixedRateScheduled() {
        log.info("不管成功与否，每隔1秒执行一次");
    }

    @Scheduled(initialDelay = 3000, fixedRate = 1000)
    public void initialDelayStringScheduled() {
        log.info("延迟3秒，每隔1秒执行一次");
    }
}
