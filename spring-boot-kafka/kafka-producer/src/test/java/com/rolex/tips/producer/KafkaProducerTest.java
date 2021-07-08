/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KafkaProducerTest {

    @Value("${kafka.topic}")
    String topic;

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void test() throws InterruptedException {
        kafkaProducer.send(topic, "hello kafka");
        Thread.sleep(5);
    }
}
