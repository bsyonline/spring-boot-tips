package com.rolex.tips.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class ActivateTest {
    @Test
    public void test1(){
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        URL url = URL.valueOf("dubbo://localhost:20880");
        List<Payment> online = payments.getActivateExtension(url, new String[]{"cardPay"}, "");
        for (Payment payment : online) {
            payment.pay(url);
        }
    }

    @Test
    public void test2(){
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        URL url = URL.valueOf("dubbo://localhost:20880?rmb=100");
//        url = url.addParameter("rmb", "100");
        List<Payment> online = payments.getActivateExtension(url,"","offline");
        for (Payment payment : online) {
            payment.pay(url);
        }
    }
}
