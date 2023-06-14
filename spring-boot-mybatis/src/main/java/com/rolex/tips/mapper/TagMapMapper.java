package com.rolex.tips.mapper;

import com.rolex.tips.entity.Tag;
import com.rolex.tips.entity.TagMap;
import com.rolex.tips.entity.User;
import com.rolex.tips.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapMapper {

    void insertTagMap(TagMap tagMap);

    List<Tag> getTagsByUserId(int userId);

}