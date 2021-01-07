package com.qf.dao;

import com.qf.pojo.Catalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CatalogMapper {

    @Select("select typename from catalog")
    List<Catalog> findAll();
}
