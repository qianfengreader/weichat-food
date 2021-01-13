package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.CatalogMapper;
import com.qf.pojo.Catalog;
import com.qf.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    CatalogMapper catalogMapper;

    @Override
    public BaseResp findAll() {

        BaseResp baseResp = new BaseResp();
        List<Catalog> all = catalogMapper.findAll();
        baseResp.setCode(200);
        baseResp.setData(all);
        return baseResp;
    }
}
