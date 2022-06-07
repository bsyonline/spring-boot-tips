/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dlq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author rolex
 * @since 2020
 */
public class ProducerTest {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("myGroup1");
        // Specify name server addresses.
        producer.setNamesrvAddr("10.69.69.232:9876");
        //Launch the instance.
        producer.start();
        Message msg = new Message("hello102" /* Topic */,
                "" /* Tag */,
                ("MESSAGE222").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        //Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
