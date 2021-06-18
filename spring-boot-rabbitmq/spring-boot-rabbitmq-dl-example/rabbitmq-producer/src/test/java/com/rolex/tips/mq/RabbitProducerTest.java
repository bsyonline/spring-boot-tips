package com.rolex.tips.mq;

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
public class RabbitProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    public void sendDeadLetterMsg() {
        for (int i = 0; i < 3; i++) {
            rabbitProducer.sendMsg("dead letter msg test " + i);
        }
    }
}