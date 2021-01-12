package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Director;

import java.util.Map;

public interface DirectorService {
    BaseResp findAll(Integer page, Integer size);

    BaseResp findByName(Object name);

    BaseResp updateDir(Director director);

    BaseResp findSearch(Map map);
}
