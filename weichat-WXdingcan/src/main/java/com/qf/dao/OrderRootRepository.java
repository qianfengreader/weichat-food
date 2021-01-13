package com.qf.dao;


import com.qf.pojo.WxOrderRoot;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 编程小石头：2501902696（微信）
 */
public interface OrderRootRepository extends JpaRepository<WxOrderRoot, Integer> {


    List<WxOrderRoot> findByBuyerOpenidAndOrderStatus(String buyerOpenid, Integer orderStatus, Sort updateTime);

    List<WxOrderRoot> findAll(Specification specification);
}
