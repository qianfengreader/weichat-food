package com.qf.dao;


import com.qf.pojo.Comments;
import com.qf.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentsMapper {
    List<Comments> findAllComments();

    List<Comments> findAllCommentsByOpenid(@Param("openid") String openid);

    Comments findCommentsByMid(@Param("id") Integer id);

    int deleteCommentsByMid(@Param("id") Integer id);

    int updateCommentsByMid(Comments comments);

    int insertCommentsByMid(Comments comments);

    User findUserByOpenid(@Param("openid")String openid);

}
