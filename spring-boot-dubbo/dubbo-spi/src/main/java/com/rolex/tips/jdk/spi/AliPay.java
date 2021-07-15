package com.rolex.tips.jdk.spi;

import lombok.extern.slf4j.Slf4j;

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
    public void pay() {
        log.info("支付宝支付");
    }
}
