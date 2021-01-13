package com.qf.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.qf.dao.LampRepository;
import com.qf.pojo.BaseResp;
import com.qf.pojo.Lamp;
import com.qf.service.LampService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class LampServiceImpl implements LampService {
    @Autowired
    LampRepository lampRepository;

    @Override
    public BaseResp findAll() {
        List<Lamp> all = lampRepository.findAll();
        BaseResp baseResp = new BaseResp();
        baseResp.setData(all);
        baseResp.setCode(200);
        baseResp.setMessage("查询成功");
        return baseResp;
    }

    @Override
    public BaseResp deleteLamp(Integer id) {
        lampRepository.deleteById(id);
        BaseResp baseResp = new BaseResp();
        baseResp.setCode(200);
        baseResp.setMessage("删除成功");
        return baseResp;
    }

    @Override
    public BaseResp saveAndFlush(Lamp lamp) {
        lampRepository.saveAndFlush(lamp);
        BaseResp baseResp = new BaseResp();
        if (lamp.getId() != null){  //修改
            baseResp.setMessage("更新成功!");
            baseResp.setCode(200);
            return baseResp;
        }
        baseResp.setMessage("更新成功!");
        baseResp.setCode(201);
        return baseResp;
    }

    @Override
    public BaseResp findById(Integer id) {
        Optional<Lamp> byId = lampRepository.findById(id);
        BaseResp baseResp = new BaseResp();
        if (byId.isPresent()){
            Object o = JSONObject.toJSON(byId);
            Lamp lamp = JSONObject.parseObject(o.toString(), Lamp.class);
            baseResp.setMessage("查询成功");
            baseResp.setCode(200);
            baseResp.setData(lamp);
            return baseResp;
        }
        baseResp.setMessage("查询失败");
        baseResp.setCode(200);
        return baseResp;
    }

    //导出
    @Override
    public BaseResp exportLamp(HttpServletResponse response) {
        List<Lamp> all = lampRepository.findAll();

        //生成excel
        //表格第一行标题内容     及   第一张table名
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图列表信息", "轮播图信息"), Lamp.class, all);
        try {
            //文件名 及 文本转换格式utf-8
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode("轮播图导出数据.xls","UTF-8"));

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseResp baseResp = new BaseResp();
        baseResp.setMessage("导出成功");


        return baseResp;
    }

}
