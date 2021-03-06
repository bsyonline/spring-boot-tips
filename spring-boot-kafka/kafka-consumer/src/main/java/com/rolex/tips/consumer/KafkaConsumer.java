/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(groupId = "group01", topics = "#{'${kafka.topic}'}")
    public void receive(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        log.info("收到消息：{}", record.value());
        ack.acknowledge();
        log.info("ack={}", ack.toString());
    }

}
