package com.qf.controller;

import com.qf.common.ResultVO;
import com.qf.service.PaihaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 排号相关
 */
@RestController
@RequestMapping("/adminPaihao")
public class AdminPaihaoController {

    @Autowired
    PaihaoService paihaoService;

    //显示所有的排号用户
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResultVO findByRuzhuo() {
        return paihaoService.findByRuzhuo();
    }


    //修改状态ruzhuo 为 1 可入桌状态
    @RequestMapping(value = "/ruzhuo",method = RequestMethod.POST)
    public ResultVO ruzhuo(@RequestBody Map map) {
        return paihaoService.findAndFlush(Integer.valueOf(map.get("id").toString()));
    }


}
