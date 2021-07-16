package com.rolex.tips.adaptive.clazz;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Adaptive
public class AdaptivePay implements Payment {
    @Override
    public void adaptivePay(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }

        // 1.从 URL 中获取 Payment 名称
        String paymentName = url.getParameter("payment");
        if (paymentName == null) {
            throw new IllegalArgumentException("paymentName == null");
        }

        // 2.通过 SPI 加载具体的 pay
        Payment paymentMaker = ExtensionLoader.getExtensionLoader(Payment.class).getExtension(paymentName);

        // 3.调用目标方法
        paymentMaker.adaptivePay(url);
    }

    @Override
    public void unadaptivePay() {
        log.info("adaptivePay-AdaptivePayMaker");
    }
}