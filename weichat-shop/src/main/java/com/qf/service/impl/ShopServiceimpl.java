package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.ShopMapper;
import com.qf.pojo.Director;
import com.qf.pojo.Shop;
import com.qf.service.ShopService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopServiceimpl implements ShopService {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ShopMapper shopMapper;
    @Override
    public BaseResp findAll() {
        BaseResp baseResp = new BaseResp();
        List<Shop> all = shopMapper.findAll();
        baseResp.setCode(200);
        baseResp.setData(all);
        System.out.println(all);
        return baseResp;
    }

    @Override
    public BaseResp deleteByName(Map map) {
        Object name = map.get("name");
        shopMapper.deleteByName(name);
        rabbitTemplate.convertAndSend("","transfer-queue",map);
        BaseResp baseResp = new BaseResp();
        baseResp.setCode(200);
        baseResp.setMessage("删除成功");
        return baseResp;
    }

    @Override
    public BaseResp updatestate(Map map) {
        BaseResp baseResp = new BaseResp();
        Object state1 = map.get("state");
        Object name1 = map.get("name");
        if (state1.equals("正常")){
            map.put("state","停业");
        }
        else if (state1.equals("停业")) {
            map.put("state","正常");
        }
        Object state = map.get("state");
        Object name = map.get("name");
        shopMapper.updatestate(name,state);
        baseResp.setCode(200);
        return baseResp;
    }

    @Override
    public BaseResp findByName(Map map) {
        BaseResp baseResp = new BaseResp();
        Shop shop = shopMapper.findByName(map.get("name"));
        if (shop!=null){
            baseResp.setCode(200);
            baseResp.setData(shop);
            baseResp.setMessage("查询成功");
        }else {
            baseResp.setCode(201);
            baseResp.setMessage("查询失败");
        }

        return baseResp;
    }

    @Override
    public BaseResp updateShop(Shop shop) {
        BaseResp baseResp = new BaseResp();
        Integer id = shop.getId();
        Shop shop1 = shopMapper.findShop(id);
        Map map = new HashMap();
        map.put("name1",shop1.getMname());
        map.put("name2",shop.getMname());
        rabbitTemplate.convertAndSend("","transfer-queuetwo",map);
        System.out.println("dddddddddddddddddddd"+shop1);
        String mname = shop.getMname();
        shop1.setMname(mname);
        String pname = shop.getPname();
        shop1.setPname(pname);
        String address = shop.getAddress();
        shop1.setAddress(address);
        String dname = shop.getDname();
        shop1.setDname(dname);
        String landline = shop.getLandline();
        shop1.setLandline(landline);
        shopMapper.updateShop(shop);

        baseResp.setCode(200);
        return baseResp;
    }

    @Override
    public BaseResp findSearch(Map map) {
        BaseResp baseResp = new BaseResp();
        String name = map.get("name").toString();
        List<Shop> search = shopMapper.findSearch('%' + name + '%');
        baseResp.setCode(200);
        baseResp.setData(search);
        return baseResp;
    }


}
