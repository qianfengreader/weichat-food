package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Menu;

public interface MenuService {

    BaseResp findAll();

    BaseResp insertMenu(Menu menu);
}
