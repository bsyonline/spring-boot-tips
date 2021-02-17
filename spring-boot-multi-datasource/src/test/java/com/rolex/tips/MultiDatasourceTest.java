package com.rolex.tips;

import com.rolex.tips.entity.Order;
import com.rolex.tips.entity.User;
import com.rolex.tips.mapper1.UserMapper;
import com.rolex.tips.mapper2.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MultiDatasourceTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void contextLoads() {
        Order order = orderMapper.findByOrderNo(1);
        Integer userId = order.getUserId();
        User user = userMapper.findById(userId);
        System.out.println(order);
        System.out.println(user);
    }

}
