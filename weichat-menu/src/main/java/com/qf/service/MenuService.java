package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Menu;

public interface MenuService {

    BaseResp findAll(Integer page,Integer size);

    BaseResp insertMenu(Menu menu);

    BaseResp findById(Integer id);

    BaseResp updateMenu(Menu menu);

    BaseResp deleteMenu(Object ids);

    BaseResp deleteMenuOne(Integer id);

    BaseResp findByCatalog(String typename);

    BaseResp getTypenameCount();
}
