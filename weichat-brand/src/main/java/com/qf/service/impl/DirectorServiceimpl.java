package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.common.BaseResp;
import com.qf.dao.DirectorMapper;
import com.qf.pojo.Director;
import com.qf.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceimpl  implements DirectorService {
    @Autowired
    DirectorMapper directorMapper;
    @Override
    public BaseResp findAll(Integer page, Integer size) {
        BaseResp baseResp = new BaseResp();
        PageHelper.startPage(page,size);
        List<Director> list = directorMapper.findAll();

        PageInfo pageInfo = new PageInfo(list);
        System.out.println(pageInfo);

        if (list!=null){
            baseResp.setCode(200);
            baseResp.setData(list);
            baseResp.setTotal(pageInfo.getTotal());
            baseResp.setMessage("查询成功");
        }else {
            baseResp.setCode(201);
            baseResp.setMessage("查询失败");
        }
        return baseResp;
    }

    @Override
    public BaseResp findByName(Object name) {
        BaseResp baseResp = new BaseResp();
        Director director = directorMapper.findByName(name);
        if (director!=null){
            baseResp.setCode(200);
            baseResp.setData(director);
        }else {
            baseResp.setCode(201);
            baseResp.setMessage("查询失败");
        }
        return baseResp;
    }

    @Override
    public BaseResp updateDir(Director director) {
        BaseResp baseResp = new BaseResp();
        directorMapper.updateDir(director);
        baseResp.setCode(200);
        return baseResp;
    }
}
