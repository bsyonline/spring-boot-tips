package com.rolex.tips.activate;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Activate(group = {"offline"}, order = 4)
public class CashPay implements Payment {
    @Override
    public void pay(URL url) {
        log.info("现金支付");
    }
}
