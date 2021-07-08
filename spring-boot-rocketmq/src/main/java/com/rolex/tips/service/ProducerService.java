package com.rolex.tips.service;

import com.rolex.tips.model.MessageDTO;
import com.rolex.tips.model.Order;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public interface ProducerService {
    /**
     * 发送消息
     *
     * @param mqMessageDTO
     * @return
     */
    boolean sendMessage(MessageDTO mqMessageDTO);

    boolean sendSeqMessage(Order order) throws Exception;
}
