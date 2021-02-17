package com.rolex.tips.dao;

import com.rolex.tips.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rolex
 * @since 2019
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
