package com.qf.service;

import com.qf.common.BaseResp;

import java.util.Map;

public interface OrderService {
    BaseResp findAllOrderByOpenid(Map map);

    BaseResp findOrderById(Map map);

    BaseResp deleteOrderById(Map map);
}
