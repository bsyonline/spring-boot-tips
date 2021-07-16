package com.rolex.tips.adaptive.method;

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
public class AdaptiveMethodTest {
    @Test
    public void test1() {
        /*
            url中没有指定
            SPI default 生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

    @Test
    public void test2() {
        /*
            @Adaptive 没指定 value = "payType"， URL中指定指定的key为接口的名字
            URL指定的生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?payment=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

    @Test
    public void test4() {
        /*
            @Adaptive 指定 value = "payType"，URL中指定指定的key为接口的名字，不生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?payment=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

    @Test
    public void test5() {
        /*
            @Adaptive 指定 value = "payType"，URL中指定指定的key为payType，生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?payType=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

}
