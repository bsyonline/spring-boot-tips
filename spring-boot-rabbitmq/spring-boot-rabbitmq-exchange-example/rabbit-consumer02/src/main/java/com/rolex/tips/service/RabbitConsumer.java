/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rolex.tips.config.RabbitConfig.*;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(queues = DIRECT_QUEUE)
    public void receiveDirectMsg(String msg, Channel channel, Message message) {
        log.info("Consumer02-{}接收到来自{}队列的Direct消息：{}", Thread.currentThread().getName(), DIRECT_QUEUE, msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("消费成功，返回ACK");
        } catch (Exception e) {
            throw new RuntimeException("消费失败:" + e.getMessage());
        }
    }

    @RabbitListener(queues = TOPIC_QUEUE2)
    public void receive(String msg, Channel channel, Message message) {
        log.info("Consumer02-{}接收到来自{}队列的Topic消息：{}", Thread.currentThread().getName(), TOPIC_QUEUE2, msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("消费成功，返回ACK");
        } catch (Exception e) {
            throw new RuntimeException("消费失败:" + e.getMessage());
        }
    }

    @RabbitListener(queues = FANOUT_QUEUE2)
    public void receiveFanoutMsg(String msg, Channel channel, Message message) {
        log.info("Consumer02-{}接收到来自{}队列的Fanout消息：{}", Thread.currentThread().getName(), FANOUT_QUEUE2, msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("消费成功，返回ACK");
        } catch (Exception e) {
            throw new RuntimeException("消费失败:" + e.getMessage());
        }
    }

    @RabbitListener(queues = HEADERS_QUEUE2)
    public void receiveHeadersMsg(String msg, Channel channel, Message message) {
        log.info("Consumer02-{}接收到来自{}队列的Headers消息：{}", Thread.currentThread().getName(), HEADERS_QUEUE2, msg);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("消费成功，返回ACK");
        } catch (Exception e) {
            throw new RuntimeException("消费失败:" + e.getMessage());
        }
    }
}
