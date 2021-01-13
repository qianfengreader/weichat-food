package com.qf.dao;

import com.qf.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WxMeNuRepository extends JpaRepository<Menu,Integer> {

    List<Menu> findByInventoryLessThan(int num);//查询库存少于num的菜品

    List<Menu> findByCnameContaining(String name);//name模糊查询


}
