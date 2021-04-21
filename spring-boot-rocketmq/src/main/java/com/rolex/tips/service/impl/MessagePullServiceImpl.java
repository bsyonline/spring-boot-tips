package com.rolex.tips.service.impl;

import com.rolex.tips.service.MessagePullService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullCallback;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class MessagePullServiceImpl implements MessagePullService {

    @Autowired
    DefaultMQPullConsumer defaultMQPullConsumer;

    @Override
    public void pull() throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        Set<MessageQueue> queues = defaultMQPullConsumer.fetchSubscribeMessageQueues("PULL_TOPIC");
        for (MessageQueue queue : queues) {
            long offset = defaultMQPullConsumer.fetchConsumeOffset(queue, false);
            // 同步拉消息
            PullResult pullResult = defaultMQPullConsumer.pull(queue, "", Math.max(0, offset), 32);
            switch (pullResult.getPullStatus()) {
                case FOUND:
                    List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                    for (MessageExt messageExt : messageExtList) {
                        String message = new String(messageExt.getBody(), "utf-8");
                        log.info("队列：{}，拉取消息：msgId={}, message={}", queue.getQueueId(), messageExt.getMsgId(), message.toString());
                    }
                    break;
                case NO_MATCHED_MSG:
                    log.info("队列：{}，NO_MATCHED_MSG", queue.getQueueId());
                    break;
                case NO_NEW_MSG:
                    log.info("队列：{}，NO_NEW_MSG", queue.getQueueId());
                    break;
                case OFFSET_ILLEGAL:
                    log.info("队列：{}，OFFSET_ILLEGAL", queue.getQueueId());
                    break;
                default:
                    break;
            }
            defaultMQPullConsumer.updateConsumeOffset(queue, pullResult.getNextBeginOffset());
        }
    }

}
