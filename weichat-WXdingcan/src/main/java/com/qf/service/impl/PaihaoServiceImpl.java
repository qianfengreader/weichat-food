package com.qf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qf.common.ResultVO;
import com.qf.dao.PaihaoRepository;
import com.qf.pojo.Paihao;
import com.qf.push.SendWxMessage;
import com.qf.service.PaihaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaihaoServiceImpl implements PaihaoService {
    @Autowired
    PaihaoRepository paihaoRepository;

    @Autowired
    SendWxMessage wxSend;

    //查
    @Override
    public ResultVO findByRuzhuo() {
        List<Paihao> byRuzhuo = paihaoRepository.findByRuzuo(0);
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setData(byRuzhuo);
        resultVO.setMsg("查询成功");
        return resultVO;
    }

    //改状态
    @Override
    public ResultVO findAndFlush(Integer id) {
        ResultVO resultVO = new ResultVO();
        Optional<Paihao> byId = paihaoRepository.findById(id);
        if (byId.isPresent()){
            Object o = JSONObject.toJSON(byId);
            Paihao paihao = JSONObject.parseObject(o.toString(), Paihao.class);
            paihao.setRuzuo(1);
            paihaoRepository.saveAndFlush(paihao);
            resultVO.setMsg("修改成功");
            resultVO.setCode(200);

            //发送订阅消息给当前排号用户
            wxSend.pushOneUser(id);

            return resultVO;
        }
        resultVO.setMsg("修改失败");
        resultVO.setCode(201);
        return resultVO;
    }


}
