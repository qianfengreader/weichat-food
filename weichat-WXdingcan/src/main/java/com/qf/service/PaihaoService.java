package com.qf.service;

import com.qf.common.ResultVO;


public interface PaihaoService {

    ResultVO findAndFlush(Integer id);

    ResultVO findByRuzhuo();
}
