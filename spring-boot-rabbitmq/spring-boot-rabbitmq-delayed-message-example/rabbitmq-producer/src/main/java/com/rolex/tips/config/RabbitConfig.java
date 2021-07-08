/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rolex
 * @since 2019
 */
@Configuration
public class RabbitConfig {

    public static final String DIRECT_EXCHANGE = "sample.delay.direct";
    public static final String DIRECT_QUEUE = "sample.delay.direct.queue";
    public static final String DIRECT_ROUTING_KEY = "sampleDirectKey";


    public static final String DELAY_EXCHANGE = "sample.delay";
    public static final String DELAY_QUEUE = "sample.delay.queue";
    public static final String DELAY_ROUTING_KEY = "sampleDelayKey";

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE) //声明死信队列Exchange
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)//声明死信队列抛出异常重定向队列的routingKey
                .withArgument("x-message-ttl", 10 * 1000) // 延迟时间
                .build();
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }

}
