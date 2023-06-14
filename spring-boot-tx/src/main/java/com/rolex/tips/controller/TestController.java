package com.rolex.tips.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2023
 */
@RestController
public class TestController {
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        return "hi, " + name;
    }
}
