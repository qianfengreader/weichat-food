package com.qf.controller;

import com.qf.common.ResultVO;
import com.qf.meiju.ResultEnum;
import com.qf.pojo.WxOrderDetail;
import com.qf.pojo.response.WxOrderResponse;
import com.qf.utils.ResultVOUtil;
import com.qf.yichang.DianCanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 卖家端订单页
 *
 */
@RestController
@RequestMapping("/adimOrder")
@Slf4j
public class AdminOrderController {

    @Autowired
    private WxOrderUtils wxOrder;


    //订单列表
    //@RequestParam("page") Integer page,
    //                         @RequestParam("size") Integer size
    @GetMapping("/list")
    public ResultVO list(){
        List<WxOrderResponse> listOrderRoot = wxOrder.findListOrderRoot();
        return ResultVOUtil.success(listOrderRoot);
    }


    //上菜完成订单
    @PostMapping("/finish")
    public ResultVO finish(@RequestBody Map map){
        try {
            WxOrderResponse one = wxOrder.findOne(Integer.valueOf(map.get("id").toString()));
            wxOrder.finish(one);
        } catch (DianCanException e) {
            ResultVO resultVO = new ResultVO();
            resultVO.setMsg(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
            resultVO.setCode(201);
            return resultVO;
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        resultVO.setCode(200);
        return resultVO;
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestBody Map map){
        try {
            WxOrderResponse one = wxOrder.findOne(Integer.valueOf(map.get("id").toString()));
            wxOrder.cancel(one);
        } catch (DianCanException e) {
            ResultVO resultVO = new ResultVO();
            resultVO.setMsg(ResultEnum.ORDER_PAY_STATUS_ERROR.getMessage());
            resultVO.setCode(201);
            return resultVO;
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        resultVO.setCode(200);
        return resultVO;
    }

    //只有取消的订单才可以删除
    @PostMapping("/remove")
    public ResultVO remove(@RequestBody Map map){
        wxOrder.remove(Integer.valueOf(map.get("id").toString()));
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("删除成功!");
        return resultVO;

    }

    //订单详情
    @PostMapping("/detail")
    public ResultVO detail(@RequestBody Map map){

        List<WxOrderDetail> id = wxOrder.findByOrderId(Integer.valueOf(map.get("orderId").toString()));
        return ResultVOUtil.success(id);

    }

    @PostMapping("/findAmount")
    public ResultVO findAmount(@RequestBody Map map) {

        WxOrderResponse orderId = wxOrder.findOne(Integer.valueOf(map.get("orderId").toString()));
        return ResultVOUtil.success(orderId);
    }
}
