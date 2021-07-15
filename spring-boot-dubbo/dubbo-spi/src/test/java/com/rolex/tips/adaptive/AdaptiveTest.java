package com.rolex.tips.adaptive;

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
public class AdaptiveTest {
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
            URL中指定
            URL指定的生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?key1=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

    @Test
    public void test3() {
        /*
            URL中指定
            实现类加@Adaptive的生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?key1=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
    }

    @Test
    public void test4() {
        /*
            URL和接口@Adaptive(value = "key1")的value不匹配
            SPI default 生效
         */
        URL url = URL.valueOf("dubbo://localhost:20880?key2=meizuPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        Payment payment = payments.getAdaptiveExtension();
        payment.adaptivePay(url);
        payment.unadaptivePay();
    }

    @Test
    public void test5() {
        URL url = URL.valueOf("dubbo://localhost:20880");
//        URL url = URL.valueOf("dubbo://localhost:20880?payment.maker=aliPay");
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
//        Payment payment = payments.getExtension("meizuPay");
        Payment payment = payments.getAdaptiveExtension();
        payment.unadaptivePay();
    }
}
