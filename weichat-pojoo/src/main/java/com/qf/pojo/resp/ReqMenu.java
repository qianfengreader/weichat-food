package com.qf.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class ReqMenu {

    private Integer id;

    private String cname;

    private Double price;

    private Integer inventory;

    private String info;

    private String pic;

    private String typename;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
}
