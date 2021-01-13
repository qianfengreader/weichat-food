package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.User;

import java.util.Map;

public interface UserService {
    BaseResp findByUsername(Map map);

    BaseResp findAll();

    BaseResp registry(User user);

    BaseResp editStatus(Integer id);

    BaseResp updateById(User user);

    BaseResp deleteById(Map map);
}
