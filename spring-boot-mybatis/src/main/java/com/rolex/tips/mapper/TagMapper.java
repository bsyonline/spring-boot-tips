package com.rolex.tips.mapper;

import com.rolex.tips.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> findAll();
}