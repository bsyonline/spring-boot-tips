package com.rolex.tips.adaptive.method;

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
    @Adaptive(value = "payType")
    void adaptivePay(URL url);

    void unadaptivePay();
}
