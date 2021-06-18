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

    public static final String DIRECT_EXCHANGE = "sample.dl.direct";
    public static final String DIRECT_QUEUE = "sample.dl.direct.queue";
    public static final String DIRECT_ROUTING_KEY = "sampleKey";

    public static final String DEAD_LETTER_EXCHANGE = "sample.dl";
    public static final String DEAD_LETTER_REDIRECT_ROUTING_KEY = "sampleDlKey";
    public static final String DEAD_LETTER_QUEUE = "sample.dl.queue";


    /**
     * 正常队列配置
     */
    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE) //声明死信队列Exchange
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_REDIRECT_ROUTING_KEY)//声明死信队列抛出异常重定向队列的routingKey
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

    /**
     * 死信队列配置
     */
    @Bean
    public Queue dlQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    @Bean
    public DirectExchange dlExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding dlBinding() {
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with(DEAD_LETTER_REDIRECT_ROUTING_KEY);
    }

}
