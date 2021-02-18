/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.dao;

import com.rolex.tips.entity.Coffee;
import com.rolex.tips.entity.Contact;
import com.rolex.tips.entity.Location;
import com.rolex.tips.entity.Order;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author rolex
 * @since 2019
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JpaTest {
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test() {
        Order order = new Order();
        order.setName("Jim");
        order.setCustomerId(1);
        order.setUserId(1);
        Contact contact = new Contact();
        contact.setEmail("jim@gmail.com");
        contact.setPhone("18622000033");
        contact.setOrder(order);
        Location location = new Location();
        location.setLat(-12.330332f);
        location.setLat(29.394888f);
        location.setContact(contact);
        contact.setLocation(location);
        order.setContact(contact);

        Coffee moche = new Coffee("moche", 100f);
        Coffee americano = new Coffee("Americano", 10.3f);
        Coffee latte = new Coffee("Latte", 103.3f);
        List<Coffee> list = Lists.newArrayList(moche, americano, latte);
        order.setCoffees(list);

        order = orderRepository.save(order);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setName("Tom");
        orderRepository.save(order);

        List<Order> orders = orderRepository.findAll();
        System.out.println(orders);
    }
}