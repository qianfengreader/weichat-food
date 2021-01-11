package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Brand;

public interface BrandService {
    BaseResp findAll();

    BaseResp addbrand(Brand brand);
}
