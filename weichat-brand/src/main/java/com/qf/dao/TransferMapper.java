package com.qf.dao;

import com.qf.pojo.Director;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 54110 on 2021/1/5.
 */
@Mapper
public interface TransferMapper {

    void deleteByName(@Param("name") Object name);

    Director findByName(Object name1);

    void updateDirector(Director director1);
}
