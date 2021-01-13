package com.qf.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class UserForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "tel")
    private String tel;    //电话
    @Column(name = "type")
    private String type;
    @Column(name = "openid")
    private String openid;  //买家微信openid

    @CreatedDate//自动添加创建时间的注解
    @Column(name = "createtime")
    private Date createTime;
    @LastModifiedDate//自动添加更新时间的注解
    @Column(name = "updatetime")
    private Date updateTime;


}
