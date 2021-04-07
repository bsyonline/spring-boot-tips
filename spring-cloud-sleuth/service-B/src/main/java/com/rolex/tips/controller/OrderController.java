package com.rolex.tips.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
public class OrderController {
    @GetMapping("/orders")
    public String list(){
        return "orders";
    }
}
