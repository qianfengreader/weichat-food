package com.qf.dao;

import com.qf.pojo.Catalog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Mapper
public interface WxCatalogRepository extends JpaRepository<Catalog,Integer> {

    List<Catalog> findByTypeIn(List<Integer> categoryTypeList);

    List<Catalog> findByType(Integer categoryType);


}
