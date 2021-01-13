package com.qf.service;

import com.qf.common.ResultVO;
import com.qf.pojo.Comment;

import java.util.List;

public interface CommentService {
    ResultVO findAll();

    ResultVO findAllByOpenid(String openid);
}
