package com.rolex.tips.service.impl;

import com.rolex.tips.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

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
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    public ConsumeConcurrentlyStatus beforeHandler(String message) {
        log.info("消息前置处理: message={}", message);
        return null;
    }

    @Override
    public ConsumeConcurrentlyStatus handle(String message) {

        try {
            // 模拟做业务操作
            log.info("消息处理: message={}", message.toString());
        } catch (Exception e) {
            log.error("handler error:{}", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @Override
    public void afterHandler(String message, Date startHandlerTime, ConsumeConcurrentlyStatus status) {
        log.info("消息后置处理: status={}", status);
    }
}
