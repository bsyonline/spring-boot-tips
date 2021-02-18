/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.dao;

import com.rolex.tips.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rolex
 * @since 2019
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {
}
