package com.qf.pojo;

import lombok.Data;

/**
 * Created by 54110 on 2020/12/11.
 */
@Data
public class BaseResp {

    private Integer code;

    private Object data;

    private String message;

    private Long total;

}
