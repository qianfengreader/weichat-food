package com.qf.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "lamp")
@ExcelTarget("lamp") /*标识  唯一区分  无实际意义*/
public class Lamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "图片编号")
    private Integer id;

    @Column(name = "info")
    @Excel(name = "轮播图介绍")
    private String info;

    @Column(name = "url")
    @Excel(name = "图片链接")
    private String url;

    @Column(name = "createtime")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    @Excel(name = "创建时间")
    private Date createtime;
}
