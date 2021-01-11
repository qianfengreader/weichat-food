package com.qf.dao;

import com.qf.pojo.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    List<Shop> findAll();

    void deleteByName(@Param("name") Object name);

    void updatestate(@Param("name") Object name,@Param("state") Object state);

    Shop findByName(@Param("name") Object name);

    void updateShop(Shop shop);

    Shop findShop(@Param("id") Integer id);
}
