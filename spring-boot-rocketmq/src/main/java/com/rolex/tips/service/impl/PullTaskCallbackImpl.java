package com.rolex.tips.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullTaskCallback;
import org.apache.rocketmq.client.consumer.PullTaskContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class PullTaskCallbackImpl implements PullTaskCallback {
    @Override
    public void doPullTask(MessageQueue messageQueue, PullTaskContext context) {
        // 1.从上下文中获取MQPullConsumer对象，此处其实就是DefaultMQPullConsumer。
        MQPullConsumer consumer = context.getPullConsumer();
        try {
            // 2.获取该消费组的该队列的消费进度
            long offset = consumer.fetchConsumeOffset(messageQueue, false);

            // 3.拉取消息，pull()方法在DefaultMQPullConsumer有具体介绍
            PullResult pullResult = consumer.pull(messageQueue, "*", Long.max(0, offset), 32);
            switch (pullResult.getPullStatus()) {
                case FOUND:
                    List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                    for (MessageExt messageExt : messageExtList) {
                        String message = new String(messageExt.getBody(), "utf-8");
                        log.info("队列：{}，拉取消息：msgId={}, message={}", messageQueue.getQueueId(), messageExt.getMsgId(), message.toString());
                    }
                    break;
                case NO_MATCHED_MSG:
                    log.info("队列：{}，NO_MATCHED_MSG", messageQueue.getQueueId());
                    break;
                case NO_NEW_MSG:
                    log.info("队列：{}，NO_NEW_MSG", messageQueue.getQueueId());
                    break;
                case OFFSET_ILLEGAL:
                    log.info("队列：{}，OFFSET_ILLEGAL", messageQueue.getQueueId());
                    break;
                default:
                    break;
            }
            // 4.更新消费组该队列消费进度
            consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());

            // 5.设置下次拉取消息时间间隔，单位毫秒
            context.setPullNextDelayTimeMillis(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
