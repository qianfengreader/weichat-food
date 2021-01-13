package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.config.RedisConfig;
import com.qf.dao.BrandMapper;
import com.qf.dao.BrandRepository;
import com.qf.pojo.Brand;
import com.qf.service.BrandService;
import com.qf.utils.ReidsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BrandServiceimpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ReidsUtils redisUtils;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    RedisConfig redisConfig;
    @Override
    public BaseResp findAll() {
        BaseResp baseResp = new BaseResp();
        List<Brand> brandList = brandMapper.findAll();
        System.out.println(brandList);
        List<Brand> all = brandMapper.findAll();
        System.out.println(all);
        baseResp.setCode(200);
        baseResp.setData(brandList);
        return baseResp;
    }

    @Override
    public BaseResp addbrand(Brand brand) {
        BaseResp baseResp = new BaseResp();
        brandRepository.saveAndFlush(brand);
        baseResp.setCode(200);
        return baseResp;
    }


    private String getToken(HttpServletRequest request) {
        //1.从cookie中获取token
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie co:cookies
        ) {
            if (co.getName().equals("token")){
                token=co.getValue();
            }
        }
        return token;
    }
}
