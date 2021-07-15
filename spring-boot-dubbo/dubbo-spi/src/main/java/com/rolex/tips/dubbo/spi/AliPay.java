package com.rolex.tips.dubbo.spi;

import lombok.extern.slf4j.Slf4j;
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
    @Adaptive
    @Override
    public void pay() {
        log.info("支付宝支付");
    }
}
