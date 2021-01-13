package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.Menu;
import com.qf.pojo.resp.Param;
import com.qf.service.MenuService;
import com.qf.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    // 上传图片
    @Autowired
    UploadUtils uploadUtils;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public BaseResp findAll(@RequestBody Param param) {

        return menuService.findAll(param.getPage(),param.getSize());
    }

    @RequestMapping(value = "/insertMenu",method = RequestMethod.POST)
    public BaseResp insertMenu(@RequestBody Menu menu) {

        return menuService.insertMenu(menu);
    }

    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    public BaseResp findById(@RequestParam("id")Integer id) {

        return menuService.findById(id);
    }

    @RequestMapping(value = "/updateMenu",method = RequestMethod.POST)
    public BaseResp updateMenu(@RequestBody Menu menu) {

        return menuService.updateMenu(menu);
    }

    @RequestMapping(value = "/deleteMenu",method = RequestMethod.POST)
    public BaseResp deleteMenu(@RequestBody Map map) {

        return menuService.deleteMenu(map.get("ids"));
    }

    @RequestMapping(value = "/deleteMenuOne",method = RequestMethod.POST)
    public BaseResp deleteMenuOne(@RequestParam("id")Integer id) {

        return menuService.deleteMenuOne(id);
    }

    @RequestMapping("/findByCatalog")
    public BaseResp findByCatalog(@RequestParam("typename")String typename) {

        return menuService.findByCatalog(typename);
    }

    // eacheras图表信息
    @RequestMapping(value = "/typenameCount", method = RequestMethod.POST)
    public BaseResp getTypenameCount() {

        return menuService.getTypenameCount();
    }

    // 上传图片
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResp uploadFile(@RequestParam("file")MultipartFile multipartFile) {

        return uploadUtils.upload(multipartFile);
    }
}
