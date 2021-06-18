package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootTest
class RabbitProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    void sendToDirect() {
        rabbitProducer.sendToDirect("direct msq test");
    }

    @Test
    void sendToTopic() {
        rabbitProducer.sendToTopic("topic msq test");
    }

    @Test
    void sendToFanout() {
        rabbitProducer.sendToFanout("fanout msq test");
    }

    @Test
    void sendToHeaders() {
        rabbitProducer.sendToHeaders("headers msq test");
    }
}