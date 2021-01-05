package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.Menu;
import com.qf.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/findAll")
    public BaseResp findAll() {

        return menuService.findAll();
    }

    @RequestMapping(value = "/insertMenu",method = RequestMethod.POST)
    public BaseResp insertMenu(@RequestBody Menu menu) {

        return menuService.insertMenu(menu);
    }
}
