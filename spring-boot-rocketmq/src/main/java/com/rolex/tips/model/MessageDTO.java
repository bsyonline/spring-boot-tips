package com.rolex.tips.model;

import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class MessageDTO {
    /**
     * 消息ID
     */
    private String msgId;
    /**
     * 主题
     */
    private String topic;
    /**
     * 消息标签体
     */
    private String tag;
    /**
     * 消息业务主键
     */
    private String key;
    /**
     * 消息体
     */
    private byte[] content;
}
