package com.qf.meiju;

import lombok.Getter;

/**
 * 菜品状态
 * 编程小石头：2501902696（微信）
 */
@Getter
public enum FoodStatusEnum implements CodeNumEnum {
    UP(0, "在架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    FoodStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
