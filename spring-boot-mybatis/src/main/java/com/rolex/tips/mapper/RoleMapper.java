package com.rolex.tips.mapper;

import com.rolex.tips.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findAll();
}