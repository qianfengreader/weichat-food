package com.qf.service;


import com.qf.pojo.BaseResp;
import com.qf.pojo.Lamp;

import javax.servlet.http.HttpServletResponse;

public interface LampService {
    BaseResp findAll();

    BaseResp deleteLamp(Integer id);

    BaseResp saveAndFlush(Lamp lamp);

    BaseResp findById(Integer id);

    BaseResp exportLamp(HttpServletResponse response);
}
