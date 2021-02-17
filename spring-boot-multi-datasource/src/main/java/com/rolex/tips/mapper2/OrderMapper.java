package com.rolex.tips.mapper2;

import com.rolex.tips.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rolex
 * @since 2020
 */
@Mapper
public interface OrderMapper {
    Order findByOrderNo(Integer orderNo);
}
