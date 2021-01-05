package com.qf.dao;

import com.qf.pojo.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select m.id,m.cname,m.price,m.inventory,m.info,m.pic,cata.typename,m.create_time as createTime,m.update_time as updateTime from menu m,catalog as cata where m.menu_type = cata.`type`")
    List<Menu> findAll();

    @Insert("insert into menu (id,cname,price,inventory,info,pic,create_time,update_time,typename) values (null,#{cname},#{price},#{inventory},#{info},#{pic},#{createTime},#{updateTime},#{typename})")
    Integer insertMenu(Menu menu);
}
