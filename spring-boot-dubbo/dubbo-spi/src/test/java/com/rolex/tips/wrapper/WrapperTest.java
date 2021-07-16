package com.rolex.tips.wrapper;

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
public class WrapperTest {
    @Test
    public void test() {
        ExtensionLoader<Payment> payments = ExtensionLoader.getExtensionLoader(Payment.class);
        URL url = URL.valueOf("dubbo://localhost:20880");
        Payment payment = payments.getExtension("aliPay");
        payment.pay(url);
    }
}
