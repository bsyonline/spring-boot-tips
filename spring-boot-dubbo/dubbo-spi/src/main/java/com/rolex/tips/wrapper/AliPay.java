package com.rolex.tips.wrapper;

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
public class AliPay implements Payment {
    @Override
    public void pay(URL url) {
        log.info("支付宝支付");
    }
}
