package com.rolex.tips.adaptive.clazz;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class WeChatPay implements Payment {
    @Override
    public void adaptivePay(URL url) {
        log.info("adaptivePay-微信支付");
    }

    @Override
    public void unadaptivePay() {
        log.info("unadaptivePay-微信支付");
    }
}
