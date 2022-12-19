package com.wh.dsy.starter.security.mapper;

import com.wh.dsy.starter.security.entity.Role;
import com.wh.dsy.starter.security.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Role> getUserRoleByUid(String uid);

    User loadUserByUsername(String username);

    Integer updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
