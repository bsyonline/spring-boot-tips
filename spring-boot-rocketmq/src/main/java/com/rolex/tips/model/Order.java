package com.rolex.tips.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@AllArgsConstructor
@Data
public class Order {
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单类型(订单创建、订单付款、订单完成）
     */
    private String type;
}
