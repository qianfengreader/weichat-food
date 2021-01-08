package com.qf.dao;

import com.qf.pojo.Menu;
import com.qf.pojo.resp.TypenameCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select m.id,m.cname,m.price,m.inventory,m.info,m.pic,typename,m.create_time as createTime,m.update_time as updateTime from menu m")
    List<Menu> findAll();

    @Insert("insert into menu (id,cname,price,inventory,info,pic,create_time,update_time,typename) values (null,#{cname},#{price},#{inventory},#{info},#{pic},#{createTime},#{updateTime},#{typename})")
    Integer insertMenu(Menu menu);

    @Select("select m.id,m.cname,m.price,m.inventory,m.info,m.pic,typename,m.create_time as createTime,m.update_time as updateTime from menu m where id = #{id}")
    Menu findById(Integer id);

    @Update("update menu set cname = #{cname}, price = #{price}, inventory = #{inventory},info = #{info},pic = #{pic},typename = #{typename},create_time = #{createTime},update_time = #{updateTime} where id = #{id}")
    Integer updateMenu(Menu menu);

    @Delete("delete from menu where id = #{id}")
    Integer delete(Integer id);

    @Select("select id,cname,price,inventory,info,pic,typename,create_time as createTime,update_time as updateTime from menu where typename = #{typename}")
    List<Menu> findByCatalog(String typename);

    // 图表信息查询
    @Select("select typename as name,count(typename) as value from menu group by typename")
    List<TypenameCount> getTypenameCount();
}
