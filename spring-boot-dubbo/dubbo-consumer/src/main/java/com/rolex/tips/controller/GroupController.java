package com.rolex.tips.controller;

import com.rolex.tips.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class GroupController {
    @DubboReference(version = "1.0.0", group = "english")
    private GreetingService englishGreetingService;
    @DubboReference(version = "1.0.0", group = "chinese")
    private GreetingService chineseGreetingService;
    @DubboReference(version = "1.0.0", group = "chinese,english", merger = "true")
    private GreetingService greetingService;

    @RequestMapping("/greeting/en")
    public String greeting1() {
        return englishGreetingService.greeting();
    }

    @RequestMapping("/greeting/cn")
    public String greeting2() {
        return chineseGreetingService.greeting();
    }

    @RequestMapping("/greeting")
    public List<String> greeting3() {
        return greetingService.menus();
    }
}
