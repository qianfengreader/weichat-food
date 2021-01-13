package com.qf.controller;

import com.qf.common.UploadUtils;
import com.qf.pojo.BaseResp;
import com.qf.pojo.Lamp;
import com.qf.service.LampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/lamp")
@Slf4j  //打印日志
public class LampController {

    @Autowired
    LampService lampService;

    @Autowired
    UploadUtils uploadUtils;

    @RequestMapping("/findAll")
    public BaseResp findAll(){
        return lampService.findAll();
    }

    //删除
    @RequestMapping("/deleteLamp")
    public BaseResp deleteLamp(@RequestBody Map map){
        return lampService.deleteLamp(Integer.valueOf(map.get("id").toString()));
    }

    //新增
    @PostMapping("/saveAndFlush")
    public BaseResp saveAndFlush(@RequestBody Lamp lamp){
        return lampService.saveAndFlush(lamp);
    }
    //查单个
    @PostMapping("/findByIdLamp")
    public BaseResp findByIdLamp(@RequestBody Map map){
        return lampService.findById(Integer.valueOf(map.get("id").toString()));
    }

    //图片上传
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResp upload(@RequestParam("file") MultipartFile multipartFile){
        return uploadUtils.upload(multipartFile);
    }

    //导出excel
    @RequestMapping(value = "/exportLamp",method = RequestMethod.GET)
    public BaseResp exportLamp(HttpServletResponse response){
        return lampService.exportLamp(response);
    }

//    //导入excel文件
//    @PostMapping("/importExcel")
//    public String importData(MultipartFile excelFile){
//        log.info("文件名:[]",excelFile.getOriginalFilename());
//        return "";
//    }

}
