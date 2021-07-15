package com.rolex.tips.adaptive;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
//@Adaptive
@Slf4j
public class AdaptivePayMaker implements Payment {
    @Override
    public void adaptivePay(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }

        // 1.从 URL 中获取 PayMaker 名称
        String paymentMakerName = url.getParameter("payment.maker");
        if (paymentMakerName == null) {
            throw new IllegalArgumentException("paymentMakerName == null");
        }

        // 2.通过 SPI 加载具体的 WheelMaker
        Payment paymentMaker = ExtensionLoader.getExtensionLoader(Payment.class).getExtension(paymentMakerName);

        // 3.调用目标方法
        paymentMaker.adaptivePay(url);
    }

    @Override
    public void unadaptivePay() {
        log.info("adaptivePay-AdaptivePayMaker");
    }
}
