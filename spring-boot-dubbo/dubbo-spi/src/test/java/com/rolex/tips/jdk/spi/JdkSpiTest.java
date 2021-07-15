package com.rolex.tips.jdk.spi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
public class JdkSpiTest {
    @Test
    public void test(){
        ServiceLoader<Payment> payments = ServiceLoader.load(Payment.class);
        Iterator<Payment> iterator = payments.iterator();
        while (iterator.hasNext()){
            Payment payment = iterator.next();
            log.info("classLoader = {}", payment.getClass().getClassLoader());
            payment.pay();
        }
    }
}
