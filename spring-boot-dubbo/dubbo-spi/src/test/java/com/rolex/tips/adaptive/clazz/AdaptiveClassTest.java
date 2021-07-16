package com.rolex.tips.adaptive.clazz;

import org.apache.dubbo.common.URL;
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
public class AdaptiveClassTest {

    @Test
    public void test1() {
        URL url = URL.valueOf("dubbo://localhost:20880?payment=weChatPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
        payment.unadaptivePay();
    }

    @Test
    public void test2() {
        URL url = URL.valueOf("dubbo://localhost:20880?payment=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
        payment.unadaptivePay();
    }

    @Test
    public void test3() {
        URL url = URL.valueOf("dubbo://localhost:20880?payment=jdPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

}
