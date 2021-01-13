package com.qf.service;

import com.qf.common.BaseResp;
import com.qf.pojo.Comments;

import java.util.Map;

public interface CommentsService {
    BaseResp findAllComments(Integer page,Integer size);

    BaseResp findAllCommentsByOpenid(Map map,Integer page,Integer size);

    BaseResp findCommentsByMid(Map map);

    BaseResp deleteCommentsByMid(Map map);

    BaseResp updateCommentsByMid(Comments comments);

    BaseResp insertCommentsByMid(Comments comments);
}
