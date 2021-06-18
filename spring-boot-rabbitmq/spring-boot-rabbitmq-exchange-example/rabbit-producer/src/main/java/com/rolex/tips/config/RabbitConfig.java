/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2019
 */
@Configuration
public class RabbitConfig {

    /**
     * direct 方式
     */
    public static final String DIRECT_EXCHANGE = "sample.direct";
    public static final String DIRECT_QUEUE = "sample.direct.queue";
    public static final String DIRECT_ROUTING_KEY = "sampleKey";

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }

    /**
     * topic 方式
     */
    public static final String TOPIC_EXCHANGE = "sample.topic";
    public static final String TOPIC_QUEUE1 = "sample.topic.queue1";
    public static final String TOPIC_QUEUE2 = "sample.topic.queue2";
    public static final String TOPIC_QUEUE3 = "sample.topic.queue3";
    public static final String TOPIC_ROUTING_KEY = "order.create.test";
    public static final String TOPIC_BINDING_KEY1 = "order.#";
    public static final String TOPIC_BINDING_KEY2 = "order.del.#";
    public static final String TOPIC_BINDING_KEY3 = "order.create.*";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2);
    }

    @Bean
    public Queue topicQueue3() {
        return new Queue(TOPIC_QUEUE3);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC_BINDING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_BINDING_KEY2);
    }

    @Bean
    public Binding topicBinding3() {
        return BindingBuilder.bind(topicQueue3()).to(topicExchange()).with(TOPIC_BINDING_KEY3);
    }

    /**
     * fanout 方式
     */
    public static final String FANOUT_EXCHANGE = "sample.fanout";
    public static final String FANOUT_QUEUE1 = "sample.fanout.queue1";
    public static final String FANOUT_QUEUE2 = "sample.fanout.queue2";
    public static final String FANOUT_QUEUE3 = "sample.fanout.queue3";

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2);
    }

    @Bean
    public Queue fanoutQueue3() {
        return new Queue(FANOUT_QUEUE3);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding3() {
        return BindingBuilder.bind(fanoutQueue3()).to(fanoutExchange());
    }

    /**
     * headers 方式
     */
    public static final String HEADERS_EXCHANGE = "sample.headers";
    public static final String HEADERS_QUEUE1 = "sample.headers.queue1";
    public static final String HEADERS_QUEUE2 = "sample.headers.queue2";
    public static final String HEADERS_QUEUE3 = "sample.headers.queue3";


    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headersQueue1() {
        return new Queue(HEADERS_QUEUE1);
    }

    @Bean
    public Queue headersQueue2() {
        return new Queue(HEADERS_QUEUE2);
    }

    @Bean
    public Queue headersQueue3() {
        return new Queue(HEADERS_QUEUE3);
    }

    @Bean
    public Binding headersBinding1() {
        Map<String, Object> map = new HashMap<>();
        map.put("type","order");
        map.put("format","xml");
        return BindingBuilder.bind(headersQueue1()).to(headersExchange())
                .whereAll(map)
                .match();
    }
    @Bean
    public Binding headersBinding2() {
        Map<String, Object> map = new HashMap<>();
        map.put("type","pay");
        map.put("format","binary");
        return BindingBuilder.bind(headersQueue2()).to(headersExchange())
                .whereAny(map)
                .match();
    }
    @Bean
    public Binding headersBinding3() {
        Map<String, Object> map = new HashMap<>();
        map.put("type","pay");
        map.put("format","json");
        return BindingBuilder.bind(headersQueue3()).to(headersExchange())
                .whereAll(map)
                .match();
    }
}
