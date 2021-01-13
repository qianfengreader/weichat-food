package com.qf.service.impl;

import com.qf.common.BaseResp;
import com.qf.dao.TransferMapper;
import com.qf.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransferServiceimpl implements TransferService {
    @Autowired
    TransferMapper transferMapper;
    @Override
    public BaseResp deleteByName(Map map) {
        BaseResp baseResp = new BaseResp();
        transferMapper.deleteByName(map.get("name"));
        baseResp.setCode(200);
        baseResp.setMessage("删除二级成功");
        return baseResp;
    }
}
