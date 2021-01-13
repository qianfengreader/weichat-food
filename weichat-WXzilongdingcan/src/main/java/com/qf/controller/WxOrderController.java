package com.qf.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.common.OrderReq;
import com.qf.common.ResultVO;
import com.qf.dao.OrderDetailRepository;
import com.qf.dao.OrderRootRepository;
import com.qf.dao.WxMeNuRepository;
import com.qf.meiju.ResultEnum;
import com.qf.pojo.WxOrderDetail;
import com.qf.pojo.response.WxOrderResponse;
import com.qf.utils.ResultVOUtil;
import com.qf.yichang.DianCanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序端用户相关
 *
 */
@RestController
@RequestMapping("/userOrder")
@Slf4j
public class WxOrderController {


    @Autowired
    private WxMeNuRepository wxMeNuRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRootRepository orderRootRepository;

    @Autowired
    private WxOrderUtils wxOrder;

    //创建订单
    @PostMapping("/create")
    @Transactional//数据库事务
    public ResultVO create(@Valid OrderReq orderReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, orderReq={}", orderReq);
            throw new DianCanException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //数据转换
        WxOrderResponse orderBean = new WxOrderResponse();
        orderBean.setBuyerName(orderReq.getName());
        orderBean.setBuyerPhone(orderReq.getPhone());
        orderBean.setBuyerAddress(orderReq.getAddress());
        orderBean.setBuyerOpenid(orderReq.getOpenid());
        List<WxOrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = new Gson().fromJson(orderReq.getItems(),
                    new TypeToken<List<WxOrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderReq.getItems());
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }
        orderBean.setOrderDetailList(orderDetailList);

        if (CollectionUtils.isEmpty(orderBean.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new DianCanException(ResultEnum.CART_EMPTY);
        }
        WxOrderResponse createResult = wxOrder.createOrder(orderBean);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", "" + createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/cuidan")
    public void cuidan(@RequestParam("zhuoHao") String zhuoHao,
                       @RequestParam("orderId") Integer orderId) {
        wxOrder.cuiDan(zhuoHao, orderId);
    }

    //订单列表
    @GetMapping("/listByStatus")
    public ResultVO listByStatus(@RequestParam("openid") String openid,
                                                        @RequestParam(value = "orderStatus", defaultValue = "0") Integer orderStatus) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }

        List<WxOrderResponse> list = new ArrayList<>();
        list.clear();


        List<WxOrderResponse> listStats = wxOrder.findListStats(openid, orderStatus);
        listStats.forEach((orderBean) -> {
            WxOrderResponse one = wxOrder.findOne(orderBean.getOrderId());
            list.add(one);
        });
        return ResultVOUtil.success(list);
    }


    //订单详情
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid") String openid,
                                            @RequestParam("orderId") int orderId) {
        WxOrderResponse orderDTO = wxOrder.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new DianCanException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return ResultVOUtil.success(orderDTO);
    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") int orderId) {
        WxOrderResponse orderDTO = wxOrder.findOne(orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new DianCanException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new DianCanException(ResultEnum.ORDER_OWNER_ERROR);
        }
        wxOrder.cancel(orderDTO);
        return ResultVOUtil.success();
    }

}
