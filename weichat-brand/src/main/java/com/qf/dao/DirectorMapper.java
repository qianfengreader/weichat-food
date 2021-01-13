package com.qf.dao;

import com.qf.pojo.Director;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DirectorMapper {

    List<Director> findAll();

    Director findByName(@Param("name") Object name);

    void updateDir(Director director);
}
