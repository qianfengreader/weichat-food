package com.qf.dao;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username")String username);

    List<User> findAll();

    Integer registry(User user);

    User findByEmail(@Param("email") String email);

    User findById(@Param("id") Integer id);

    Integer updateUserById(User user);

    Integer updateById(User user);

    Integer deleteById(@Param("id") Integer id);
}
