package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    //根据手机号查询个人的全部订单
    @RequestMapping("/findAllOrderByOpenid")
    public BaseResp findAllOrderByOpenid(@RequestBody Map map){
        return orderService.findAllOrderByOpenid(map);
    }

    @RequestMapping("/findOrderById")
    public BaseResp findOrderById(@RequestBody Map map){
        return orderService.findOrderById(map);
    }

    @RequestMapping("/deleteOrderById")
    public BaseResp deleteOrderById(@RequestBody Map map){
        return orderService.deleteOrderById(map);
    }


}
