package com.qf.controller;


import com.qf.common.BaseResp;
import com.qf.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/searchKey")
    public BaseResp searchKey(@RequestParam("key")String key,@RequestParam("page")Integer page,@RequestParam("size")Integer size){

        return searchService.searchKey(key,page,size);
    }
}
