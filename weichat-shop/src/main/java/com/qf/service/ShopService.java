package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Shop;

import java.util.Map;

public interface ShopService {
    BaseResp findAll();

    BaseResp deleteByName(Map map);

    BaseResp updatestate(Map map);

    BaseResp findByName(Map map);

    BaseResp updateShop(Shop shop);
}
