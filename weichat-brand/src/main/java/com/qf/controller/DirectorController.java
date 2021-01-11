package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.Director;
import com.qf.service.DirectorService;
import com.qf.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    TransferService transferService;

    @Autowired
    DirectorService directorService;
    @RequestMapping(value = "/deleteByName",method = RequestMethod.POST)
    public BaseResp deleteByName(@RequestBody Map map){
        return transferService.deleteByName(map);
    }
    @RequestMapping(value = "/findAll/{page}/{size}",method = RequestMethod.GET)
    public BaseResp findAll(@PathVariable("page")Integer page, @PathVariable("size")Integer size){
        return directorService.findAll(page,size);
    }
    @RequestMapping(value = "/findByName",method = RequestMethod.POST)
    public BaseResp findByName(@RequestBody Map map){
        return directorService.findByName(map.get("name"));
    }
    @RequestMapping(value = "/updateDir",method = RequestMethod.POST)
    public BaseResp updateDir(@RequestBody Director director){
        System.out.println(director);
        return directorService.updateDir(director);
    }
}
