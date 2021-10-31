package com.rolex.tips.service;

import com.rolex.tips.config.FeignConfig;
import com.rolex.tips.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rolex
 * @since 2019
 */
@FeignClient(name = "feign-server", configuration = FeignConfig.class)
@Service
public interface OrderService {

    @GetMapping(value = "/orders/{id}")
    Order findById(@PathVariable("id") String id);

    @GetMapping(value = "/orders")
    List<Order> list();

    @PostMapping("/orders")
    Order add(@RequestBody Order order);

    @GetMapping("/orders/types")
    List<Order> findByType(@RequestParam("type") String type);
}
