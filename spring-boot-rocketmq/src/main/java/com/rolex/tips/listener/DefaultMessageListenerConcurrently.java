package com.rolex.tips.listener;

import com.rolex.tips.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {
    @Autowired
    ConsumerService consumerService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        ConsumeConcurrentlyStatus consumerStatus = null;
        try {
            for (MessageExt messageExt : list) {
                log.info("MsgId={}", messageExt.getMsgId());
                String message = new String(messageExt.getBody(), "utf-8");
                // 预处理
                consumerService.beforeHandler(message);
                // 处理
                consumerStatus = consumerService.handle(message);
                // 处理之后
                consumerService.afterHandler(message, new Date(), consumerStatus);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("消费失败");
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return consumerStatus;
    }
}
