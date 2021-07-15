package com.rolex.tips.adaptive;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class AliPay implements Payment {
    @Override
    public void adaptivePay(URL url) {
        log.info("adaptivePay-支付宝支付");
    }

    @Override
    public void unadaptivePay() {
        log.info("unadaptivePay-支付宝支付");
    }
}
