package com.rolex.tips.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

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
public class SequenceMessageConsumerServiceImpl implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        //获取消息
        MessageExt msg = list.get(0);
        //消费者获取消息 这里只输出 不做后面逻辑处理
        log.info("Consumer-线程名称={}, 消息={}", Thread.currentThread().getName(), new String(msg.getBody()));
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
