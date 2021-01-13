package com.qf.common;

import lombok.Data;

import java.util.List;

/**
 * http请求返回的最外层对象
 *
 */
@Data
public class ResultVO {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private Object data;
}
