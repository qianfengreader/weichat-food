package com.qf.dao;

import com.qf.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
