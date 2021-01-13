package com.qf.controller;

import com.qf.common.PaihaoVO;
import com.qf.common.ResultVO;
import com.qf.dao.PaihaoRepository;
import com.qf.pojo.Paihao;
import com.qf.utils.ResultVOUtil;
import com.qf.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 小程序排号相关接口
 */
@RestController
@RequestMapping("/paihao")
@Slf4j
public class WxPaihaoController {
    @Autowired
    PaihaoRepository repository;

    //排号接口 type:0小桌，1大桌
    @PostMapping("/quhao")
    public ResultVO quhao(@RequestParam(value = "openid") String openid,
                          @RequestParam(value = "type") Integer type,
                          @RequestParam("templateid") String templateid) {
        //条件查询,size代表当前已经排位的最后一个号码(当日)  默认为0
        int size = repository.findByDayAndType(TimeUtils.getYMD(), type).size();
        log.info("当前排号数={}", size);
        Paihao paihao = new Paihao();
        paihao.setDay(TimeUtils.getYMD());
        paihao.setNum(size + 1);
        paihao.setType(type);
        paihao.setOpenid(openid);
        paihao.setRuzuo(0);
        paihao.setTemplateid(templateid);
        return ResultVOUtil.success(repository.save(paihao));    }

    /*
     * 查询当前排号
     * */
    @GetMapping("/getNum")
    public ResultVO getNum(@RequestParam("openid") String openid) {
        //获取当前小桌入座情况
        List<Paihao> listSmall = repository.findByDayAndRuzuoAndTypeOrderByNum(TimeUtils.getYMD(), 1, 0);
        //获取当前大桌入座情况
        List<Paihao> listBig = repository.findByDayAndRuzuoAndTypeOrderByNum(TimeUtils.getYMD(), 1, 1);
        //查询当前客户的排号情况
        List<Paihao> listKeHu = repository.findByOpenidAndDay(openid,TimeUtils.getYMD());
        //组装数据返回给小程序端
        PaihaoVO paihao = new PaihaoVO();
        if (listSmall != null && listSmall.size() > 0) {
            paihao.setSmallOkNum(listSmall.get(listSmall.size() - 1).getNum());
        } else {
            paihao.setSmallOkNum(0);
        }
        if (listBig != null && listBig.size() > 0) {
            paihao.setBigOkNum(listBig.get(listBig.size() - 1).getNum());
        } else {
            paihao.setBigOkNum(0);
        }
        if (listKeHu != null && listKeHu.size() > 0) {
            paihao.setNum(listKeHu.get(listKeHu.size() - 1).getNum());
            paihao.setType(listKeHu.get(listKeHu.size() - 1).getType());
        } else {
            paihao.setNum(0);
            paihao.setType(0);
        }
        return ResultVOUtil.success(paihao);
    }

}
