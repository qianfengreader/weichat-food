package com.qf.service;

import com.qf.common.ResultVO;

public interface CommentService {
    ResultVO findAll();

    ResultVO findAllByOpenid(String openid);
}
