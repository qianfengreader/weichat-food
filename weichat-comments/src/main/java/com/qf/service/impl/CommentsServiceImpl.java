package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.common.BaseResp;
import com.qf.dao.CommentsMapper;
import com.qf.pojo.Comments;
import com.qf.pojo.User;
import com.qf.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public BaseResp findAllComments(Integer page,Integer size) {
        BaseResp baseResp = new BaseResp();
        PageHelper.startPage(page,size);
        List<Comments> commentsList = commentsMapper.findAllComments();
        if (commentsList==null){
            baseResp.setMessage("暂无评论");
            baseResp.setCode(201);
            return baseResp;
        }
        PageInfo<Comments> commentsPageInfo = new PageInfo<>(commentsList);

        baseResp.setMessage("查询成功");
        baseResp.setCode(200);
        baseResp.setData(commentsList);
        baseResp.setTotal(commentsPageInfo.getTotal());
        return baseResp;

    }

    @Override
    public BaseResp findAllCommentsByOpenid(Map map,Integer page,Integer size) {
        BaseResp baseResp = new BaseResp();
        String openid =(String) map.get("openid");
        //通过openID查询是否有该用户
        /*if (openid==null || openid==""){

            return findAllComments(page,size);
        }*/
        User user = commentsMapper.findUserByOpenid(openid);
        if (user==null){
            baseResp.setMessage("查无此人,请输入正确的用户标识");
            baseResp.setCode(202);
            return baseResp;
        }
        PageHelper.startPage(page,size);
        List<Comments> commentsList = commentsMapper.findAllCommentsByOpenid(openid);
        if (commentsList==null){
            baseResp.setMessage("暂无评论");
            baseResp.setCode(201);
            return baseResp;
        }
        PageInfo<Comments> commentsPageInfo = new PageInfo<>(commentsList);

        baseResp.setMessage("查询成功");
        baseResp.setCode(200);
        baseResp.setData(commentsList);
        baseResp.setTotal(commentsPageInfo.getTotal());
        return baseResp;

    }

    @Override
    public BaseResp findCommentsByMid(Map map) {
        BaseResp baseResp = new BaseResp();
        Integer id =(Integer) map.get("id");
        Comments comments = commentsMapper.findCommentsByMid(id);
        if (comments==null){
            baseResp.setMessage("查询失败");
            baseResp.setCode(201);
            return baseResp;
        }
        baseResp.setMessage("查询成功");
        baseResp.setCode(200);
        baseResp.setData(comments);
        return baseResp;
    }

    @Override
    public BaseResp deleteCommentsByMid(Map map) {
        BaseResp baseResp = new BaseResp();
        Integer id =(Integer) map.get("id");
        int i = commentsMapper.deleteCommentsByMid(id);
        if (i>0){
            baseResp.setMessage("删除成功");
            baseResp.setCode(200);
            return baseResp;
        }
        baseResp.setMessage("删除失败");
        baseResp.setCode(201);
        return baseResp;
    }

    @Override
    public BaseResp updateCommentsByMid(Comments comments) {
        BaseResp baseResp = new BaseResp();
        int i = commentsMapper.updateCommentsByMid(comments);
        if (i>0){
            baseResp.setMessage("修改成功");
            baseResp.setCode(200);
            return baseResp;
        }
        baseResp.setMessage("修改失败");
        baseResp.setCode(201);
        return baseResp;
    }

    @Override
    public BaseResp insertCommentsByMid(Comments comments) {
        BaseResp baseResp = new BaseResp();
        int i = commentsMapper.insertCommentsByMid(comments);
        if (i>0){
            baseResp.setMessage("添加成功");
            baseResp.setCode(200);
            return baseResp;
        }
        baseResp.setMessage("添加失败");
        baseResp.setCode(201);
        return baseResp;
    }
}
