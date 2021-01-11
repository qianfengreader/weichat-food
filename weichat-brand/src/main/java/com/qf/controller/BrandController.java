package com.qf.controller;

import com.qf.common.BaseResp;

import com.qf.pojo.Brand;
import com.qf.service.BrandService;
import com.qf.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    UploadUtils uploadUtils;
    @Autowired
    BrandService brandService;
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public BaseResp upload(@RequestParam("file") MultipartFile multipartFile){
        return uploadUtils.upload(multipartFile);
    }
    @RequestMapping(value = "/addbrand",method = RequestMethod.POST)
    public BaseResp addbrand(@RequestBody Brand brand){
        System.out.println(brand);
        return brandService.addbrand(brand);
    }
    @RequestMapping("/findAll")
    public BaseResp findAll(){
        return brandService.findAll();
    }


}
