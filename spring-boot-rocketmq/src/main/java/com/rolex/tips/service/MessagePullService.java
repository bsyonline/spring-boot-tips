package com.rolex.tips.service;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface MessagePullService {

    void pull() throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException;
}
