/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.tips.handler;

import com.rolex.tips.entity.Skill;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author rolex
 * @since 2020
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Skill.class})
public class SkillHandler extends BaseTypeHandler<Skill> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Skill skill, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, skill.getValue());
    }

    @Override
    public Skill getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return Skill.nameOf(resultSet.getString(s));
    }

    @Override
    public Skill getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return Skill.nameOf(resultSet.getString(i));
    }

    @Override
    public Skill getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return Skill.nameOf(callableStatement.getString(i));
    }
}
