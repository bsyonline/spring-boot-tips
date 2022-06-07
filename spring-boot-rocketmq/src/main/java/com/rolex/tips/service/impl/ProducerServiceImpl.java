package com.rolex.tips.service.impl;

import com.alibaba.fastjson.JSON;
import com.rolex.tips.model.MessageDTO;
import com.rolex.tips.model.Order;
import com.rolex.tips.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public boolean sendMessage(MessageDTO mqMessageDTO) {
        log.info("开始发送消息, messageDTO={}", JSON.toJSONString(mqMessageDTO));
        SendResult sendResult;
        try {
            Message message = new Message(mqMessageDTO.getTopic(), mqMessageDTO.getTag(), mqMessageDTO.getKey(), mqMessageDTO.getContent());
            sendResult = defaultMQProducer.send(message);
        } catch (Exception e) {
            log.error("消息发送失败, mqMessageDTO={}, cause={}", JSON.toJSONString(mqMessageDTO), e);
            return false;
        }
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("发送成功, sendResult={}", JSON.toJSONString(sendResult));
            return true;
        }
        return false;
    }

    @Override
    public boolean sendSeqMessage(Order order) throws Exception {
        Message message = new Message("SEQ_TOPIC", "", order.getOrderId(), order.toString().getBytes());
        SendResult sendResult = defaultMQProducer.send(message, (MessageQueueSelector) (list, message1, o) -> {
            //3、arg的值其实就是下面传入 orderId
            String orderId = (String) o;
            //4、因为订单是String类型，所以通过hashCode转成int类型
            int hashCode = orderId.hashCode();
            //5、因为hashCode可能为负数 所以取绝对值
            hashCode = Math.abs(hashCode);
            //6、保证同一个订单号 一定分配在同一个queue上
            long index = hashCode % list.size();
            return list.get((int) index);
        }, order.getOrderId(), 50000);
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.info("发送成功, 发送状态={}, queue={} ,orderId={}, type={}", sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(), order.getOrderId(), order.getType());
            return true;
        }
        return false;
    }
}
