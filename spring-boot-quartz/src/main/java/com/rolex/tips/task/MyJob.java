package com.rolex.tips.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
//持久化
@PersistJobDataAfterExecution
//禁止并发执行(Quartz不要并发地执行同一个job定义（这里指一个job类的多个实例）)
@DisallowConcurrentExecution
@Slf4j
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("MyJob data: {}", context.getJobDetail().getJobDataMap().get("port"));
    }
}
