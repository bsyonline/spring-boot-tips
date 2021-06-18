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
public class RabbitmqConfig {

    /**
     * 正常队列配置
     */
    public static final String DIRECT_EXCHANGE = "sample.delay.direct";
    public static final String DIRECT_QUEUE = "sample.delay.direct.queue";
    public static final String DIRECT_ROUTING_KEY = "sampleDirectKey";

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE) //声明死信队列Exchange
                .withArgument("x-dead-letter-routing-key", DELAY_ROUTING_KEY)//声明死信队列抛出异常重定向队列的routingKey
                .withArgument("x-message-ttl", 10 * 1000) // 延时时间
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }

    /**
     * 死信队列配置
     */
    /*public static final String DEAD_LETTER_EXCHANGE = "sample.delay.dl";
    public static final String DEAD_LETTER_QUEUE = "sample.delay.dl.queue";
    public static final String DEAD_LETTER_REDIRECT_ROUTING_KEY = "sampleDelayDlKey";

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
    }*/

    /**
     * 延时队列配置
     */
    public static final String DELAY_EXCHANGE = "sample.delay";
    public static final String DELAY_QUEUE = "sample.delay.queue";
    public static final String DELAY_ROUTING_KEY = "sampleDelayKey";

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE).build();
    }

    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY);
    }

}
