package com.qf.dao;

import com.qf.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {


    List<Order> findAllOrderByOpenid(@Param("openid") String openid);

    Order findOrderById(@Param("id") Integer id);

    int deleteOrderById(@Param("id") Integer id);
}
