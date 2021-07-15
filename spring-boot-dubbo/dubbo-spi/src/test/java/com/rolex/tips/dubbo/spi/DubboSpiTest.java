package com.rolex.tips.dubbo.spi;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class DubboSpiTest {
    @Test
    public void test1() {
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment aliPay = payments.getExtension("aliPay");
        log.info("classLoader = {}", aliPay.getClass().getClassLoader());
        aliPay.pay();
        Payment weChatPay = payments.getExtension("weChatPay");
        log.info("classLoader = {}", weChatPay.getClass().getClassLoader());
        weChatPay.pay();
    }

    @Test
    public void test2() {
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getDefaultExtension();
        log.info("classLoader = {}", payment.getClass().getClassLoader());
        payment.pay();
    }

    @Test
    public void test3() {
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        log.info("classLoader = {}", payment.getClass().getClassLoader());
        payment.pay();
    }
}
