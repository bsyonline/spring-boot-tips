/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.tips.mapper;

import com.rolex.tips.entity.User;
import com.rolex.tips.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author rolex
 * @since 2019
 */
@Mapper // 和@MapperScan二选一
public interface UserMapper {

    @Insert("insert into t_user (name, age, gender, skill) values (#{name}, #{age}, #{gender}, #{skill})")
    int save(User user);

    @Select("select id, name, age, gender, skill from t_user")
    List<User> findAll();

    List<User> findByCondition(UserVO user);

    List<User> findByAnyCondition(UserVO user);

    void update(User user);

    int batchSave(List<User> list);

    List<User> batchQuery(List<Integer> ids);

    int batchDelete(List<Integer> ids);

    void batchUpdate(List<User> list);

    List<User> listForPage();

    List<User> findByNameAndAge(@Param("name") String arg1, @Param("age") Integer arg2);

    List<User> findByAge(Integer age);

    List<Map<Integer, Integer>> groupByColumn(@Param("columnName") String columnName);

    List<User> testQuery(@Param("num") Integer num);

}
