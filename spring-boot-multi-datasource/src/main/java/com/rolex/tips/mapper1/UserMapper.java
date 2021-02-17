package com.rolex.tips.mapper1;

import com.rolex.tips.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rolex
 * @since 2020
 */
@Mapper
public interface UserMapper {
    User findById(Integer id);
}
