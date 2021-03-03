/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String msg) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("发送失败：{}", throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("发送成功：{}", result.toString());
            }
        });
    }

}
