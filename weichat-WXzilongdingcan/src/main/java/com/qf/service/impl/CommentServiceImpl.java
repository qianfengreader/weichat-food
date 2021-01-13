package com.qf.service.impl;

import com.qf.common.ResultVO;
import com.qf.dao.CommentMapper;
import com.qf.pojo.Comment;
import com.qf.service.CommentService;
import com.qf.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.ibatis.ognl.DynamicSubscript.all;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;


    //查询所有评论
    @Override
    public ResultVO findAll() {
        List<Comment> all = commentMapper.findAll();
        return ResultVOUtil.success(all);
    }

    @Override
    public ResultVO findAllByOpenid(String openid) {
        List<Comment> allByOpenid = commentMapper.findAllByOpenid(openid);
        return ResultVOUtil.success(allByOpenid);
    }

}
