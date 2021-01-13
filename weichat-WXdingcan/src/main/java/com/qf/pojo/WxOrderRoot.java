package com.qf.pojo;

import com.qf.meiju.OrderStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单总单
 * 编程小石头：2501902696（微信）
 */
@Entity
@Data
@Table(name = "orderroot")
public class WxOrderRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @Column(name = "buyerName")
    private String buyerName;
    @Column(name = "buyerPhone")
    private String buyerPhone;
    @Column(name = "buyerAddress")
    private String buyerAddress;//桌号
    @Column(name = "buyerOpenid")
    private String buyerOpenid;//微信用户唯一标识
    @Column(name = "orderAmount")
    private BigDecimal orderAmount;//合计
    @Column(name = "orderStatus")
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();//订单状态, 默认为0新下单.
    @Column(name = "payStatus")
    private Integer payStatus = 0;//支付状态, 默认为0未支付
    @Column(name = "cuidan")
    private Integer cuidan = 0;//被催单次数

    @Column(name = "createTime")
    @CreatedDate//自动添加创建时间的注解
    private Date createTime;

    @Column(name = "updateTime")
    @LastModifiedDate//自动添加更新时间的注解
    private Date updateTime;

}
