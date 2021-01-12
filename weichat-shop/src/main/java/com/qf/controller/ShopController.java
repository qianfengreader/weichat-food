package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.Shop;
import com.qf.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @RequestMapping("/findAll")
    public BaseResp findAll(){
        return shopService.findAll();
    }

    @RequestMapping(value = "/deleteByName",method = RequestMethod.POST)
    public BaseResp deleteByName(@RequestBody Map map){
        return shopService.deleteByName(map);
    }
    @RequestMapping(value = "/updatestate",method = RequestMethod.POST)
    public BaseResp updatestate(@RequestBody Map map){
        return shopService.updatestate(map);
    }
    @RequestMapping(value = "/findByName",method = RequestMethod.POST)
    public BaseResp findByName(@RequestBody Map map){
        return shopService.findByName(map);
    }
    @RequestMapping(value = "/updateShop",method = RequestMethod.POST)
    public BaseResp updateShop(@RequestBody Shop shop){
        System.out.println(shop);
        return shopService.updateShop(shop);
    }
    @RequestMapping(value = "/findSearch",method = RequestMethod.POST)
    public BaseResp findSearch(@RequestBody Map map){
        return shopService.findSearch(map);
    }
}
