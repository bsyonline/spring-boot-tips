package com.rolex.tips.mapper;

import com.rolex.tips.entity.Role;
import com.rolex.tips.entity.User;
import com.rolex.tips.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void insertUserRole(UserRole userRole);

    List<Role> getRolesByUserId(int userId);

    List<User> getUsersByRoleId(int roleId);


}