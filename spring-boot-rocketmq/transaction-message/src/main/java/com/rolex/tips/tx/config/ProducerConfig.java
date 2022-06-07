package com.rolex.tips.tx.config;

import com.rolex.tips.tx.listener.UserRegTransactionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
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

    @Autowired
    UserRegTransactionListener userRegTransactionListener;

    @Bean
    public TransactionMQProducer transactionMQProducer() {
        TransactionMQProducer producer = new TransactionMQProducer("tx_topic");
        producer.setNamesrvAddr(namesrvAddr);
        producer.setTransactionListener(userRegTransactionListener);
        try {
            producer.start();
            log.info("tx producer started, namesrvAddr={}, groupName={}", "tx_group", namesrvAddr);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("生产者启动异常", e);
        }
        return producer;
    }
}
