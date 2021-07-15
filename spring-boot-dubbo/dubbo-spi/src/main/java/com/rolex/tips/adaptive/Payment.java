package com.rolex.tips.adaptive;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
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
    @Adaptive(value = "key1")
    void adaptivePay(URL url);

    void unadaptivePay();
}
