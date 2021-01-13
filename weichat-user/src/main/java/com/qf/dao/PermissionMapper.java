package com.qf.dao;

import com.qf.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> findPermissionByUserName(@Param("username") String username);
}
