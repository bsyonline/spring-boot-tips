/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KafkaProducerTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    public void test(){
        kafkaProducer.send("topic01", "hello kafka");
    }
}
