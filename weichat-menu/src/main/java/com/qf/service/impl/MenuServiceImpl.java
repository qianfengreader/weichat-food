package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.MenuMapper;
import com.qf.pojo.Menu;
import com.qf.pojo.ReqMenu;
import com.qf.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public BaseResp findAll() {

        BaseResp baseResp = new BaseResp();
        List<Menu> all = menuMapper.findAll();
        baseResp.setCode(200);
        baseResp.setData(all);
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
}
