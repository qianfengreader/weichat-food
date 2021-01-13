package com.qf.controller;

import com.qf.common.BaseResp;
import com.qf.pojo.Comments;
import com.qf.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    //查询所有的评论
    @RequestMapping("/findAllComments")
    public BaseResp findAllComments(@RequestParam("page")Integer page,@RequestParam("size")Integer size) {
        return commentsService.findAllComments(page,size);
    }

    //查询个人的所有评论
    @RequestMapping("/findAllCommentsByOpenid")
    public BaseResp findAllCommentsByOpenid(@RequestBody Map map,@RequestParam("page")Integer page,@RequestParam("size")Integer size) {
        return commentsService.findAllCommentsByOpenid(map,page,size);
    }

    //查询某个菜品的评论根据菜品id
    @RequestMapping("/findCommentsByMid")
    public BaseResp findCommentsByMid(@RequestBody Map map) {
        return commentsService.findCommentsByMid(map);
    }

    //删除某个菜品的评论根据菜品id
    @RequestMapping("/deleteCommentsByMid")
    public BaseResp deleteCommentsByMid(@RequestBody Map map) {
        return commentsService.deleteCommentsByMid(map);
    }

    //修改某个菜品的评论根据菜品id
    @RequestMapping("/updateCommentsByMid")
    public BaseResp updateCommentsByMid(@RequestBody Comments comments) {
        return commentsService.updateCommentsByMid(comments);
    }

    //增加某个菜品的评论根据菜品id
    @RequestMapping("/insertComments")
    public BaseResp insertCommentsByMid(@RequestBody Comments comments) {
        return commentsService.insertCommentsByMid(comments);
    }


}
