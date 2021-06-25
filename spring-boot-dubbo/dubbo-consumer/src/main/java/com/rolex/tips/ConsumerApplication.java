/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips;

import com.rolex.tips.api.DemoService;
import com.rolex.tips.api.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
@SpringBootApplication
@Slf4j
@RestController
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @DubboReference(version = "1.0.0")
    private DemoService demoService;
    @DubboReference(version = "1.0.0", group = "english")
    private GreetingService englishGreetingService;
    @DubboReference(version = "1.0.0", group = "chinese")
    private GreetingService chineseGreetingService;
    @DubboReference(version = "1.0.0", group = "chinese,english", merger = "true")
    private GreetingService greetingService;

    @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return demoService.sayHello(name);
    }

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
