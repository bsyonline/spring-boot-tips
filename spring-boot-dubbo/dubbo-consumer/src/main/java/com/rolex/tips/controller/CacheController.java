package com.rolex.tips.controller;

import com.rolex.tips.api.CacheService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CacheController {
    @DubboReference(version = "1.0.0"/*, cache = "true"*/)
    CacheService cacheService;

    @RequestMapping("/cache/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return cacheService.sayHello(name);
    }
}
