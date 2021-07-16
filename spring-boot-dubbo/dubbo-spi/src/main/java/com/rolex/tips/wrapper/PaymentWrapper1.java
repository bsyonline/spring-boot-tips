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
public class PaymentWrapper1 implements Payment {

    Payment payment;

    public PaymentWrapper1(Payment payment) {
        this.payment = payment;
    }

    @Override
    public void pay(URL url) {
        log.info("pre1");
        payment.pay(url);
        log.info("after1");
    }
}
