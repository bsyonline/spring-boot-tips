package com.rolex.tips.adaptive;

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
public class WeChatPay implements Payment {
    @Override
    public void pay(URL url) {
        log.info("微信支付");
    }
}
