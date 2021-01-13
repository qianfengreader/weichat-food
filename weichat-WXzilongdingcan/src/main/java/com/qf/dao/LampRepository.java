package com.qf.dao;

import com.qf.pojo.Lamp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LampRepository extends JpaRepository<Lamp,Integer> {
}
