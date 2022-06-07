package com.rolex.tips.tx.service;

import com.rolex.tips.tx.model.User;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface UserService {

    boolean reg(User user) throws MQClientException;
}
