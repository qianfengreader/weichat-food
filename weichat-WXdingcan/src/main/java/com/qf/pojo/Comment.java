package com.qf.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "utel")
    private String utel;    //用户电话
    @Column(name = "mid")
    private Integer mid;    //订单id
    @Column(name = "openid")
    private String openid;  //用户id
    @Column(name = "avatarurl")
    private String avatarUrl;//头像
    @Column(name = "score")
    private Integer score;  //分数
    @Column(name = "info")
    private String info;    //内容
    @Column(name = "createtime")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private Date createtime;
    @Column(name = "name")
    private String name;//用户姓名
}
