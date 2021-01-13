package com.qf.pojo;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "typename")
    private String typename;
    @Column(name = "type")
    private Integer type;
}
