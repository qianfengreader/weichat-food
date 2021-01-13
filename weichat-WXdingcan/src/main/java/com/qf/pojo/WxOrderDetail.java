package com.qf.pojo;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 用户订单--订单详情
 *
 */
@Entity
@Data
@Table(name = "orderdetail")
public class WxOrderDetail {
    @Id
    @GeneratedValue
    private Integer detailId;
    @Column(name = "orderId")
    private Integer orderId;//订单id.
    @Column(name = "foodId")
    private int foodId;//菜品id
    @Column(name = "foodName")
    private String foodName;
    @Column(name = "foodPrice")
    private BigDecimal foodPrice;
    @Column(name = "foodQuantity")
    private Integer foodQuantity;//下单食物数量
    @Column(name = "foodIcon")
    private String foodIcon;//商品图

}
