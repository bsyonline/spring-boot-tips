package com.rolex.tips.dubbo.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SPI("weChatPay")
public interface Payment {
    void pay();
}
