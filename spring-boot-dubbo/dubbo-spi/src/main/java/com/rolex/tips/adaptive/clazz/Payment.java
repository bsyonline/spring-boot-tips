package com.rolex.tips.adaptive.clazz;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SPI
public interface Payment {

    void adaptivePay(URL url);

    void unadaptivePay();
}
