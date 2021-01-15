package com.qf.controller;

import com.qf.dao.OrderDetailRepository;
import com.qf.dao.OrderRootRepository;
import com.qf.dao.WxMeNuRepository;
import com.qf.meiju.OrderStatusEnum;
import com.qf.meiju.ResultEnum;
import com.qf.pojo.Menu;
import com.qf.pojo.WxOrderDetail;
import com.qf.pojo.WxOrderRoot;
import com.qf.pojo.response.WxCardResponse;
import com.qf.pojo.response.WxOrderResponse;
import com.qf.utils.EnumUtil;
import com.qf.websocket.WebSocketServer;
import com.qf.yichang.DianCanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信小程序端订单相关
 *
 */
@Service
@Slf4j
public class WxOrderUtils {
    @Autowired
    private WxMeNuRepository wxMeNuRepository;
    @Autowired
    private WebSocketServer webSocket;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRootRepository orderRootRepository;


    //创建订单的方法
    public WxOrderResponse createOrder(WxOrderResponse orderBean) {
        log.error("小程序提交上来的订单={}", orderBean);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//订单总价格

        //计算订单总价
        for (WxOrderDetail orderDetail : orderBean.getOrderDetailList()) {
            //如果id不存在   则返回null 防止程序报错(标准写法)
            Menu foodInfo = wxMeNuRepository.findById(orderDetail.getFoodId()).orElse(null);
            //异常捕获500后跟错误原因  返回给前端
            if (foodInfo == null) {
                throw new DianCanException(ResultEnum.PRODUCT_NOT_EXIST);//无此菜品
            }
            orderAmount = foodInfo.getPrice()
                    .multiply(new BigDecimal(orderDetail.getFoodQuantity()))
                    .add(orderAmount);//计算订单总价
        }
        //写入总订单数据库  操作实体类
        WxOrderRoot orderMaster = new WxOrderRoot();
        //list  复制
        //import org.springframework.beans.BeanUtils;   (a,b)   a转化为b
        //import org.apache.commons.beanutils.BeanUtils     (a,b)   b转化为a
        BeanUtils.copyProperties(orderBean, orderMaster);//把orderBean转换为orderMaster
        orderMaster.setOrderAmount(orderAmount);    //订单总价
        orderMaster.setOrderStatus(OrderStatusEnum.NEW_PAYED.getCode());//已支付的标识 1
        //save  与   saveflush
        //save，只暂时保留在内存中，直到发出flush或commit命令
        WxOrderRoot orderRoot = orderRootRepository.save(orderMaster);
        for (WxOrderDetail orderDetail : orderBean.getOrderDetailList()) {
            Menu foodInfo = wxMeNuRepository.findById(orderDetail.getFoodId()).orElse(null);
            //订单详情入库
            orderDetail.setOrderId(orderRoot.getOrderId());
            //BeanUtils.copyProperties(foodInfo, orderDetail);
            orderDetail.setFoodIcon(foodInfo.getPic());
            orderDetail.setFoodName(foodInfo.getCname());
            orderDetail.setFoodPrice(foodInfo.getPrice());
            orderDetailRepository.save(orderDetail);
        }

        //扣menu库存
        List<WxCardResponse> cartDTOList = orderBean.getOrderDetailList().stream().map(e ->
                new WxCardResponse(e.getFoodId(), e.getFoodQuantity())
        ).collect(Collectors.toList());

        for (WxCardResponse cartDTO : cartDTOList) {
            Menu food = wxMeNuRepository.findById(cartDTO.getProductId()).orElse(null);
            if (food == null) {
                throw new DianCanException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = food.getInventory() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new DianCanException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            food.setInventory(result);
            wxMeNuRepository.save(food);
        }
        try {
            log.error("下单,,,,orderDTO={}", orderBean);
            webSocket.sendMessage("0", "0");//发送websocket消息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderBean;
    }

    //用户催单
    public void cuiDan(String zhuoHao, Integer orderId) {
        try {
            WxOrderRoot orderRoot = orderRootRepository.findById(orderId).orElse(null);
            if (orderRoot != null) {
                int cuidan = orderRoot.getCuidan();
                orderRoot.setCuidan(cuidan + 1);//催单次数加一
                orderRootRepository.save(orderRoot);
            }
            webSocket.sendMessage("桌号为" + zhuoHao + ";  订单号为" + orderId, "0");//发送websocket消息
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询单个订单
    public WxOrderResponse findOne(Integer orderId) {
        WxOrderRoot orderMaster = orderRootRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new DianCanException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<WxOrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new DianCanException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        WxOrderResponse orderDTO = new WxOrderResponse();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setOrderStatusStr(orderDTO.getOrderStatusStr(orderDTO.getOrderStatus()));


        return orderDTO;
    }

    //查询单个订单  orderdetail
    public List<WxOrderDetail> findByOrderId(Integer orderId){
        List<WxOrderDetail> byOrderId = orderDetailRepository.findByOrderId(orderId);
        return byOrderId;
    }

//    public Page<WxOrderResponse> findList(String buyerOpenid, Pageable pageable) {
//        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
//
//        List<WxOrderResponse> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
//
//        return new PageImpl<WxOrderResponse>(orderDTOList, pageable, orderMasterPage.getTotalElements());
//    }

    public List<WxOrderResponse> findListOrderRoot() {
        List<WxOrderRoot> all = orderRootRepository.findAll();
        List<WxOrderResponse> wxOrderResponses = orderResponse(all);

        return wxOrderResponses;
    }


        //查询不同订单状态列表
    public List<WxOrderResponse> findListStats(String buyerOpenid, Integer orderStatus) {

        List<WxOrderRoot> orderMasters = orderRootRepository.findByBuyerOpenidAndOrderStatus(buyerOpenid, orderStatus,
                Sort.by(Sort.Direction.DESC, "updateTime"));
        return orderResponse(orderMasters);
    }

    //删除订单
    @Transactional
    public boolean remove(Integer orderId) {
        if (orderId == null) {
            throw new DianCanException(ResultEnum.ORDER_NOT_EXIST);
        }
        orderRootRepository.deleteById(orderId);
        List<WxOrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for (WxOrderDetail detail : orderDetails) {
            orderDetailRepository.deleteById(detail.getDetailId());
        }
        return true;
    }

    //取消订单
    @Transactional
    public WxOrderResponse cancel(WxOrderResponse orderDTO) {
        WxOrderRoot orderMaster = new WxOrderRoot();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_PAYED.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DianCanException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        WxOrderRoot updateResult = orderRootRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new DianCanException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new DianCanException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<WxCardResponse> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new WxCardResponse(e.getFoodId(), e.getFoodQuantity()))
                .collect(Collectors.toList());
        for (WxCardResponse cartDTO : cartDTOList) {
            Menu food = wxMeNuRepository.findById(cartDTO.getProductId()).orElse(null);
            log.error("订单里的菜品id={},查询的菜品={}", cartDTO.getProductId(), food);
            if (food == null) {
                throw new DianCanException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = food.getInventory() + cartDTO.getProductQuantity();
            food.setInventory(result);
            wxMeNuRepository.save(food);
        }

        return orderDTO;
    }

    //完结订单
    @Transactional
    public WxOrderResponse finish(WxOrderResponse orderDTO) {
        //提示用户支付
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DianCanException(ResultEnum.ORDER_NO_PAY);
        }
        //只有已支付订单才可以完结订单
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW_PAYED.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new DianCanException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        WxOrderRoot orderMaster = new WxOrderRoot();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        WxOrderRoot updateResult = orderRootRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new DianCanException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    public Page<WxOrderResponse> findList(Pageable pageable) {
        Page<WxOrderRoot> orderMasterPage = orderRootRepository.findAll(pageable);

        List<WxOrderResponse> orderDTOList = orderResponse(orderMasterPage.getContent());

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /*
     * 类型转换的工具类
     * */
    private WxOrderResponse convert1(WxOrderRoot orderMaster) {
        WxOrderResponse orderDTO = new WxOrderResponse();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    private List<WxOrderResponse> orderResponse(List<WxOrderRoot> orderMasterList) {
        return orderMasterList.stream().map(e ->
                convert1(e)
        ).collect(Collectors.toList());
    }

//    //导出订单数据到excel
//    public void exportOrderToExcel(HttpServletResponse response) throws IOException {
//        String fileName = "订单导出";
//        String[] titles = {"订单id", "用户姓名", "用户手机号", "桌号", "订单金额", "订单状态", "下单时间"};
//        List<WxOrderRoot> rootList = orderRootRepository.findAll();
//        int size = rootList.size();
//        String[][] dataList = new String[size][titles.length];
//        for (int i = 0; i < size; i++) {
//            WxOrderRoot orderRoot = rootList.get(i);
//            dataList[i][0] = "" + orderRoot.getOrderId();
//            dataList[i][1] = orderRoot.getBuyerName();
//            dataList[i][2] = orderRoot.getBuyerPhone();
//            dataList[i][3] = orderRoot.getBuyerAddress();
//            dataList[i][4] = "" + orderRoot.getOrderAmount();
//            dataList[i][5] = EnumUtil.getByCode(orderRoot.getOrderStatus(), OrderStatusEnum.class).getMessage();
//            dataList[i][6] = "" + orderRoot.getCreateTime();
//        }
//        ExcelExportUtils.createWorkbook(fileName, titles, dataList, response);
//    }
}
