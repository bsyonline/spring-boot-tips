/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static com.rolex.tips.config.RabbitConfig.*;


/**
 * @author rolex
 * @since 2019
 */
@Component
@Slf4j
public class RabbitProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("收到ACK,消息发送成功: {}", correlationData);
        } else {
            log.error("消息发送失败: {}", cause);
            CorrelationDataWrapper messageCorrelationDataWrapper = (CorrelationDataWrapper) correlationData;
            String exchange = messageCorrelationDataWrapper.getExchange();
            Object message = messageCorrelationDataWrapper.getMessage();
            String routingKey = messageCorrelationDataWrapper.getRoutingKey();
            int retryCount = messageCorrelationDataWrapper.getRetryCount();
            //重试次数+1
            ((CorrelationDataWrapper) correlationData).setRetryCount(retryCount + 1);
            rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, correlationData);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String exchange, String routingKey) {
        // confirm 只能保证消息到达 broker，但是是否到达 queue 不能确定，所以需要记录 return message
        // 如果消息没有投递到 queue，消息会被退回
        log.error("消费退回: {}", message);
    }

    public void sendToDirect(String msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("向DirectExchange发送消息: {}", msg);
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, DIRECT_ROUTING_KEY, msg, correlationId);
        log.info("结束发送消息: {}", msg);
    }

    public void sendToTopic(String msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("向TopicExchange发送消息: {}", msg);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, TOPIC_ROUTING_KEY, msg, correlationId);
        log.info("结束发送消息: {}", msg);
    }

    public void sendToFanout(String msg) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("向FanoutExchange发送消息: {}", msg);
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null, msg, correlationId);
        log.info("结束发送消息: {}", msg);
    }

    public void sendToHeaders(String msg) {
        log.info("向HeadersExchange发送消息1: {}", (msg + 1));
        MessageProperties properties = new MessageProperties();
        properties.setHeader("type", "order");
        properties.setHeader("format", "xml");
        Message message = new Message((msg + 1).getBytes(), properties);
        rabbitTemplate.send(HEADERS_EXCHANGE, null, message);
        log.info("结束发送消息1: {}", (msg + 1));
        log.info("----");
        log.info("向HeadersExchange发送消息2: {}", (msg + 2));
        MessageProperties properties1 = new MessageProperties();
        properties1.setHeader("type", "pay");
        properties1.setHeader("format", "json");
        Message message1 = new Message((msg + 2).getBytes(), properties1);
        rabbitTemplate.send(HEADERS_EXCHANGE, null, message1);
        log.info("结束发送消息2: {}", (msg + 2));
        log.info("----");
        log.info("向HeadersExchange发送消息3: {}", (msg + 3));
        MessageProperties properties2 = new MessageProperties();
        properties2.setHeader("type", "order");
        properties2.setHeader("format", "json");
        Message message2 = new Message((msg + 3).getBytes(), properties2);
        rabbitTemplate.send(HEADERS_EXCHANGE, null, message2);
        log.info("结束发送消息3: {}", (msg + 3));
    }
}
