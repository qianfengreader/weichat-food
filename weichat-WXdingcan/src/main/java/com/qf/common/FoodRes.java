package com.qf.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回给小程序的菜品页
 *
 */
@Data
public class FoodRes {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String cname;

    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("stock")
    private Integer inventory;//库存

    @JsonProperty("desc")
    private String info;    //描述

    @JsonProperty("icon")
    private String pic;
}
