package com.qf.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "paihao")
public class Paihao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "num")
    private Integer num;
    @Column(name = "type")
    private Integer type;//0小桌，1大桌
    @Column(name = "openid")
    private String openid;//用户的唯一标识
    @Column(name = "ruzuo")
    private Integer ruzuo;//是否入桌
    @Column(name = "day")
    private String day;//代表那一天
    @Column(name = "templateid")
    private String templateid;//小程序订阅消息推送的模板id

    @CreatedDate//自动添加创建时间的注解
    private Date createTime;
    @LastModifiedDate//自动添加更新时间的注解
    private Date updateTime;
}
