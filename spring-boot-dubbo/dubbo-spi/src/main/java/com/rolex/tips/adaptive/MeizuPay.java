package com.rolex.tips.adaptive;

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
public class MeizuPay implements Payment {
    @Override
    public void adaptivePay(URL url) {
        log.info("adaptivePay-魅族支付");
    }

    @Override
    public void unadaptivePay() {
        log.info("unadaptivePay-魅族支付");
    }
}
