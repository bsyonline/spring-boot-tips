package com.rolex.tips.controller;

import com.rolex.tips.model.Order;
import com.rolex.tips.model.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

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
    public Response list(){
        return Response.success(new ArrayList());
    }

    @PostMapping("/orders/add")
    public Response add(){
        return Response.success(new Order(1, new Date()));
    }

    @PutMapping("/orders/update")
    public Response update(){
        return Response.success(new Order(1, new Date()));
    }

    @GetMapping("/orders/{id}")
    public Response get(@PathVariable("id") int id){
        return Response.success(new Order(1, new Date()));
    }
}
