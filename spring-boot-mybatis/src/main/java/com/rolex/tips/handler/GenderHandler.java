/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.handler;

import com.rolex.tips.entity.Gender;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author rolex
 * @since 2020
 */
@Component
@MappedJdbcTypes({JdbcType.TINYINT})
@MappedTypes({Gender.class})
public class GenderHandler implements TypeHandler<Gender> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Gender gender, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, gender.getValue());
    }

    @Override
    public Gender getResult(ResultSet resultSet, String s) throws SQLException {
        return Gender.nameOf(resultSet.getInt(s));
    }

    @Override
    public Gender getResult(ResultSet resultSet, int i) throws SQLException {
        return Gender.nameOf(resultSet.getInt(i));
    }

    @Override
    public Gender getResult(CallableStatement callableStatement, int i) throws SQLException {
        return Gender.nameOf(callableStatement.getInt(i));
    }
}
