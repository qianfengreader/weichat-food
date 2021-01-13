package com.qf.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目)
 * 编程小石头：2501902696（微信）
 */
@Data
public class LeimuVO {

    @JsonProperty("name")
    private String leimuName;

    @JsonProperty("type")
    private Integer leimuType;

    @JsonProperty("foods")
    private List<FoodRes> foodResList;
}
