package com.qf.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Menu {

    private Integer id;

    private String cname;

    private Double price;

    private Integer inventory;

    private String info;

    private String pic;

    private Integer menuType;

    private Date createTime;

    private Date updateTime;
}
