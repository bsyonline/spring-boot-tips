/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.alibaba.fastjson.JSON;
import com.rolex.tips.model.MessageDTO;
import com.rolex.tips.model.Order;
import com.rolex.tips.service.MessagePullService;
import com.rolex.tips.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author rolex
 * @since 2020
 */
@RestController
@SpringBootApplication
@Slf4j
public class RocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketApplication.class, args);
    }

    @Autowired
    ProducerService producerService;

    @GetMapping("/msg/{type}")
    public String test(@PathVariable("type") String type) {
        String topic = null;
        switch (type) {
            case "1":
                topic = "PUSH_TOPIC";
                break;
            case "2":
                topic = "PULL_TOPIC";
                break;
            case "3":
                topic = "PULL_CALLBACK_TOPIC";
                break;
            default:
                topic = "";
                break;
        }
        String finalTopic = topic;
        IntStream.range(1, 21).forEach(i -> {
            MessageDTO mqMessageDTO = new MessageDTO();
            mqMessageDTO.setTopic(finalTopic);
            mqMessageDTO.setTag("");
            mqMessageDTO.setContent(JSON.toJSONString(i + "_" + LocalDateTime.now()).getBytes());
            boolean success = producerService.sendMessage(mqMessageDTO);
            if (success) {
                log.info("消息发送成功");
            } else {
                log.info("消息发送失败");
            }
        });
        return topic;
    }

    @Autowired
    MessagePullService messagePullService;

    @GetMapping("/pull")
    public String pull() throws InterruptedException, RemotingException, UnsupportedEncodingException, MQClientException, MQBrokerException {
        messagePullService.pull();
        return "OK";
    }

    @GetMapping("/seqMsg")
    public String seqMsg() throws Exception {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("XXX001", "订单创建"));
        orderList.add(new Order("XXX001", "订单付款"));
        orderList.add(new Order("XXX001", "订单完成"));
        orderList.add(new Order("XXX002", "订单创建"));
        orderList.add(new Order("XXX002", "订单付款"));
        orderList.add(new Order("XXX002", "订单完成"));
        orderList.add(new Order("XXX003", "订单创建"));
        orderList.add(new Order("XXX003", "订单付款"));
        orderList.add(new Order("XXX003", "订单完成"));
        for (Order order : orderList) {
            boolean b = producerService.sendSeqMessage(order);
            if (b) {
                log.info("消息发送成功");
            } else {
                log.info("消息发送失败");
            }
        }
        return "OK";
    }

    @GetMapping("/testDLQ/{msg}")
    public String testDLQ(@PathVariable("msg")String msg) throws Exception {

        MessageDTO mqMessageDTO = new MessageDTO();
        mqMessageDTO.setTopic("hello");
        mqMessageDTO.setTag("");
        mqMessageDTO.setContent(JSON.toJSONString((msg+"_111")).getBytes(StandardCharsets.UTF_8));
        boolean success = producerService.sendMessage(mqMessageDTO);
        System.out.println(success);
        MessageDTO mqMessageDTO1 = new MessageDTO();
        mqMessageDTO1.setTopic("world");
        mqMessageDTO1.setTag("");
        mqMessageDTO1.setContent(JSON.toJSONString((msg+"_222")).getBytes(StandardCharsets.UTF_8));
        boolean success1 = producerService.sendMessage(mqMessageDTO1);
        System.out.println(success1);
        return "OK";
    }

}
