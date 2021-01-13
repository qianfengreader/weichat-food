package com.qf.controller;

import com.qf.common.ResultVO;
import com.qf.dao.CommentRepository;
import com.qf.dao.OrderRootRepository;
import com.qf.meiju.OrderStatusEnum;
import com.qf.meiju.ResultEnum;
import com.qf.pojo.Comment;
import com.qf.pojo.WxOrderRoot;
import com.qf.pojo.response.WxOrderResponse;
import com.qf.service.CommentService;
import com.qf.utils.ResultVOUtil;
import com.qf.yichang.DianCanException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc:评论相关
 */
@RestController
public class WxCommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private CommentRepository repository;
    @Autowired
    private WxOrderUtils wxOrder;
    @Autowired
    private OrderRootRepository masterRepository;

    //订单详情
    @PostMapping("/comment")
    public ResultVO detail(@RequestParam("openid") String openid,
                                    @RequestParam("mid") int mid,
                                    @RequestParam("name") String name,
                                    @RequestParam("avatarurl") String avatarurl,
                                    @RequestParam("info") String info) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(mid)) {
            throw new DianCanException(ResultEnum.PARAM_ERROR);
        }
        //提交评论
        Comment comment = new Comment();
        comment.setName(name);
        comment.setAvatarUrl(avatarurl);
        comment.setOpenid(openid);
        comment.setInfo(info);
        Comment save = repository.save(comment);

        //修改订单状态
        WxOrderResponse orderDTO = wxOrder.findOne(mid);
        orderDTO.setOrderStatus(OrderStatusEnum.COMMENT.getCode());
        WxOrderRoot orderMaster = new WxOrderRoot();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        masterRepository.save(orderMaster); //保存
        return ResultVOUtil.success(save);
    }

    //所有评论
    @GetMapping("/commentList")
    public ResultVO commentList() {
        return commentService.findAll();
    }

    //单个用户的所有评论
    @GetMapping("/userCommentList")
    public ResultVO userCommentList(@RequestParam("openid") String openid) {
        return commentService.findAllByOpenid(openid);
    }
}
