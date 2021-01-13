package com.qf.dao;

import com.qf.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findAll();

    //查看本用户评价
    List<Comment> findAllByOpenid(@Param("openid") String openid);
}
