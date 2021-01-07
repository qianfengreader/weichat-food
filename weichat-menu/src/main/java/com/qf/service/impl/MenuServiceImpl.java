package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.common.BaseResp;
import com.qf.dao.MenuMapper;
import com.qf.pojo.Menu;
import com.qf.pojo.ReqMenu;
import com.qf.pojo.resp.TypenameCount;
import com.qf.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public BaseResp findAll(Integer page,Integer size) {

        BaseResp baseResp = new BaseResp();
        // 分页查询
        PageHelper.startPage(page,size);
        List<Menu> all = menuMapper.findAll();

        PageInfo<Menu> pageInfo = new PageInfo<>(all);
        baseResp.setCode(200);
        baseResp.setData(all);
        baseResp.setTotal(pageInfo.getTotal());
        baseResp.setMessage("查询成功");
        return baseResp;
    }

    @Override
    public BaseResp insertMenu(Menu menu) {

        BaseResp baseResp = new BaseResp();
        Integer integer = menuMapper.insertMenu(menu);
        baseResp.setCode(200);
        baseResp.setMessage("添加成功");
        return baseResp;
    }

    @Override
    public BaseResp findById(Integer id) {

        BaseResp baseResp = new BaseResp();
        Menu byId = menuMapper.findById(id);
        baseResp.setCode(200);
        baseResp.setData(byId);
        return baseResp;
    }

    @Override
    public BaseResp updateMenu(Menu menu) {

        BaseResp baseResp = new BaseResp();
        Integer integer = menuMapper.updateMenu(menu);
        baseResp.setMessage("修改成功");
        baseResp.setCode(200);
        return baseResp;
    }

    @Override
    public BaseResp deleteMenu(Object ids) {
        BaseResp baseResp = new BaseResp();
        List<Integer> id = (List) ids;
        for (int i=0; i < id.size(); i++) {
            menuMapper.delete(id.get(i));
        }
        baseResp.setCode(200);
        baseResp.setMessage("删除成功");
        return baseResp;
    }

    @Override
    public BaseResp deleteMenuOne(Integer id) {

        BaseResp baseResp = new BaseResp();
        Integer delete = menuMapper.delete(id);
        baseResp.setCode(200);
        baseResp.setMessage("删除成功");
        return baseResp;
    }

    // 根据一级分类查询二级分类
    @Override
    public BaseResp findByCatalog(String typename) {

        BaseResp baseResp = new BaseResp();
        List<Menu> menuList = menuMapper.findByCatalog(typename);
        if (menuList == null || menuList.size() == 0) {
            baseResp.setCode(200);
            baseResp.setMessage("数据查询结果为空");
            return baseResp;
        }
        baseResp.setData(menuList);
        baseResp.setMessage("查询成功");
        baseResp.setCode(200);
        return baseResp;
    }

    // 图标信息
    @Override
    public BaseResp getTypenameCount() {

        BaseResp baseResp = new BaseResp();
        List<TypenameCount> typenameCount = menuMapper.getTypenameCount();
        if (typenameCount == null || typenameCount.size() == 0) {
            baseResp.setMessage("获取图表失败");
            baseResp.setCode(200);
            return baseResp;
        }
        baseResp.setCode(200);
        baseResp.setData(typenameCount);
        baseResp.setMessage("获取图信息成功");
        return baseResp;
    }
}
