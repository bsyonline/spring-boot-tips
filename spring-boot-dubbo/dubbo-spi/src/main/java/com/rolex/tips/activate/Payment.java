package com.rolex.tips.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
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
    void pay(URL url);
}
