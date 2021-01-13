package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.OrderMapper;
import com.qf.pojo.Order;
import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;


    @Override
    public BaseResp findAllOrderByOpenid(Map map) {
        BaseResp baseResp = new BaseResp();
        String openid =(String) map.get("openid");
        List<Order> orderList = orderMapper.findAllOrderByOpenid(openid);
        if (orderList==null){
            baseResp.setMessage("暂无订单信息");
            baseResp.setCode(201);
            return baseResp;
        }
        baseResp.setCode(200);
        baseResp.setMessage("查询成功");
        baseResp.setData(orderList);
        return baseResp;
    }

    @Override
    public BaseResp findOrderById(Map map) {
        BaseResp baseResp = new BaseResp();
        Integer id =(Integer) map.get("id");
        Order order = orderMapper.findOrderById(id);
        if (order==null){
            baseResp.setMessage("查询订单详情失败");
            baseResp.setCode(201);
            return baseResp;
        }
        baseResp.setCode(200);
        baseResp.setMessage("查询成功");
        baseResp.setData(order);
        return baseResp;
    }

    @Override
    public BaseResp deleteOrderById(Map map) {
        BaseResp baseResp = new BaseResp();
        Integer id =(Integer) map.get("id");
        int i = orderMapper.deleteOrderById(id);
        if (i>0){
            baseResp.setCode(200);
            baseResp.setMessage("删除成功");
            return baseResp;
        }
        baseResp.setCode(201);
        baseResp.setMessage("删除失败");
        return baseResp;
    }
}
