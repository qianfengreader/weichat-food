package com.qf.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "bname")
    private String bname;
    @Column(name = "sname")
    private String sname;
    @Column(name = "tel")
    private String tel;
    @Column(name = "pic")
    private String pic;
    @Column(name = "region")
    private String region;
}
