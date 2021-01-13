package com.qf.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Comments {


    private Integer id;

    private String utel;

    private String info;

    private Integer mid;

    private  Integer score;

    private String openid;

    private String username;

    private String avatarurl;

    private Date createtime;

}
