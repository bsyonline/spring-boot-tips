package com.rolex.tips.controller;

import com.rolex.tips.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class DemoController {
    @DubboReference(version = "1.0.0",
            //registry = {"beijing", "shanghai"},
            sticky = true,
            lazy = true,
            cluster = "failover",
            loadbalance = "roundrobin",
            //mock = "return 11",
            actives = 10,
            connections = 10)
    private DemoService demoService;

    @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        demoService.register(name);
        return demoService.sayHello(name);
    }
}
