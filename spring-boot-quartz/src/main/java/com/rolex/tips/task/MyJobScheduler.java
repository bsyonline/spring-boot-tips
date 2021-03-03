package com.rolex.tips.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

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
public class MyJobScheduler {
    @Autowired
    private Scheduler scheduler;
    public static AtomicInteger count = new AtomicInteger(0);
    private static String TRIGGER_GROUP_NAME = "myTrigger";
    private static String JOB_GROUP_NAME = "myGroup";
    private static String JOB_NAME = "myJob";
    @Value("${server.port}")
    int port;

    public void start() {
        // 防止重复执行
        if (count.incrementAndGet() <= 1) {
            initMyJob();
        }
    }

    public void initMyJob() {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME, TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                // 创建任务
                JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                        .withIdentity(JOB_NAME, JOB_GROUP_NAME)
                        .build();

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
                // 创建trigger
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(JOB_NAME, TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();

                //设置任务传递数据
                jobDetail.getJobDataMap().put("port", port);

                // 将触发器与任务绑定到调度器内
                scheduler.scheduleJob(jobDetail, trigger);
                log.info("quartz create job: {}", jobDetail.getKey());
            } else {
                log.info("job is existed: {}", trigger.getKey());
            }

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
