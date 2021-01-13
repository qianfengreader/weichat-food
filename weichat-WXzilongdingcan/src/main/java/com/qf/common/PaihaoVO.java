package com.qf.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 组装数据（排号相关）
 *
 */
@Data
public class PaihaoVO {
    private Integer num;
    private Integer type;
    private Integer smallOkNum;//小桌当前就位号码
    private Integer bigOkNum;//大桌当前就位号码


    //    @JsonProperty("name")
//    private String leimuName;

    //    @JsonProperty("type")
//    private Integer leimuType;

    //    @JsonProperty("foods")
//    private List<FoodRes> foodResList;
}
