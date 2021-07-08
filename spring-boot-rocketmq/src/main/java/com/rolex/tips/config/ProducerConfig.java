package com.rolex.tips.config;

import com.rolex.tips.exception.MQException;
import com.rolex.tips.listener.DefaultMessageListenerConcurrently;
import com.rolex.tips.service.impl.PullTaskCallbackImpl;
import com.rolex.tips.service.impl.SequenceMessageConsumerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumerScheduleService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
@Slf4j
public class ProducerConfig {
    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.groupName}")
    private String groupName;

    /**
     * 创建普通消息发送者实例
     *
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("producer started, namesrvAddr={}, groupName={}", namesrvAddr, groupName);
        return producer;
    }

    @Autowired
    DefaultMessageListenerConcurrently defaultConsumerListener;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener(defaultConsumerListener);
        try {
            consumer.subscribe("PUSH_TOPIC", "*");
            consumer.start();
            log.info("push consumer started, namesrvAddr={}, groupName={}", groupName, namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer start error, namesrvAddr={}, groupName={}", groupName, namesrvAddr, e);
            consumer.shutdown();
            throw new MQException("", e);
        }
        return consumer;
    }

    @Bean
    public DefaultMQPullConsumer defaultMQPullConsumer() {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("group2");
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.start();
            log.info("pull consumer started, namesrvAddr={}, groupName={}", groupName, namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer start error, namesrvAddr={}, groupName={}", groupName, namesrvAddr, e);
            consumer.shutdown();
            throw new MQException("", e);
        }
        return consumer;
    }

    @Autowired
    PullTaskCallbackImpl pullTaskCallback;

    //    @Bean
    public MQPullConsumerScheduleService scheduleService() throws MQClientException {
        // 1. 实例化对象
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService("group3");
        // 2. 设置NameServer
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr(namesrvAddr);
        // 3. 设置消费组为集群模式
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        // 4. 注册拉取回调函数
        scheduleService.registerPullTaskCallback("PULL_CALLBACK_TOPIC", pullTaskCallback);
        scheduleService.start();
        return scheduleService;
    }

    @Autowired
    SequenceMessageConsumerServiceImpl sequenceMessageConsumerService;

    @Bean
    public DefaultMQPushConsumer seqMQPushConsumer() throws MQException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group4");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener(sequenceMessageConsumerService);
        try {
            consumer.subscribe("SEQ_TOPIC", "*");
            consumer.start();
            log.info("push consumer started, namesrvAddr={}, groupName={}", groupName, namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer start error, namesrvAddr={}, groupName={}", groupName, namesrvAddr, e);
            consumer.shutdown();
            throw new MQException("", e);
        }
        return consumer;
    }
}
