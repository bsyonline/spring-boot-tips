package com.rolex.tips.wrapper;

import org.apache.dubbo.common.URL;
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
    void pay(URL url);
}
