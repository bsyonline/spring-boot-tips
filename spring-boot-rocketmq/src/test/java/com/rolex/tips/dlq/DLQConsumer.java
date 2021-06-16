/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.dlq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class DLQConsumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("c2");

        // Specify name server addresses.
        consumer.setNamesrvAddr("localhost:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("%DLQ%dlq_test_group", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages%n", Thread.currentThread().getName());
                /*DefaultMQProducer producer = new DefaultMQProducer("dlq_test_group");
                // Specify name server addresses.
                producer.setNamesrvAddr("localhost:9876");
                //Launch the instance.
                try {
                    producer.start();
                    for (int i = 0; i < msgs.size(); i++) {
                        String msgId = msgs.get(i).getMsgId();
                        String msg = new String(msgs.get(i).getBody());
                        String msg1 = msg.replace("_dlq", "");
                        System.out.printf("%s Receive New Messages: %s, %s%n", Thread.currentThread().getName(), msgId, msg);
                        Message message = new Message("dlq_test" *//* Topic *//*,
                                "" *//* Tag *//*,
                                (msg1).getBytes(RemotingHelper.DEFAULT_CHARSET) *//* Message body *//*
                        );
                        SendResult sendResult = producer.send(message);
                        System.out.printf("%s%n", sendResult);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Call send message to deliver message to one of brokers.

                //Shut down once the producer instance is not longer in use.
                producer.shutdown();*/

                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
