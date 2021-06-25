package com.rolex.tips.service;

import com.google.common.collect.Lists;
import com.rolex.tips.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@DubboService(group = "english", version = "1.0.0")
public class EnglishGreetingServiceImpl implements GreetingService {

    @Override
    public String greeting() {
        return "hello";
    }

    @Override
    public List<String> menus() {
        return Lists.newArrayList("start");
    }
}
