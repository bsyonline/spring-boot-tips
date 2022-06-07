package com.rolex.tips.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.rolex.tips.tx.model.User;
import com.rolex.tips.tx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    TransactionMQProducer transactionMQProducer;

    @Override
    public boolean reg(User user) throws MQClientException {
        try {
            log.info("send tx msg start");
            TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(new Message("tx_topic", null, user.getName(), JSON.toJSONString(user).getBytes()), null);
            log.info("send tx msg end, result={}", sendResult.getSendStatus());
            if (sendResult.getSendStatus().equals(SendStatus.SEND_OK) &&
                    !sendResult.getLocalTransactionState().equals(LocalTransactionState.ROLLBACK_MESSAGE)) {
                log.info("send tx msg success");
                return true;
            } else {
                log.info("send tx msg failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("send tx msg failed");
            return false;
        }
    }
}
