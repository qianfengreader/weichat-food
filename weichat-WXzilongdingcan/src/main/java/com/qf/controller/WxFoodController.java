package com.qf.controller;


import com.qf.common.FoodRes;
import com.qf.common.LeimuVO;
import com.qf.common.ResultVO;
import com.qf.dao.WxCatalogRepository;
import com.qf.dao.WxMeNuRepository;
import com.qf.pojo.Catalog;
import com.qf.pojo.Menu;
import com.qf.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 小程序买家端,菜品列表
 * 
 */
@RestController
@Slf4j
public class WxFoodController {

    @Autowired
    private WxMeNuRepository wxMeNuRepository;

    @Autowired
    private WxCatalogRepository wxCatalogRepository;

    /*
     * 返回菜单和菜品列表
     * */
    @GetMapping("/buyerfoodList")
    public ResultVO list(@RequestParam("searchKey") String searchKey) {
        log.info("搜索词={}", searchKey);
        List<Menu> foodList = new ArrayList<>();
        if (StringUtils.pathEquals("all", searchKey)) {
            //返回所有菜品
            foodList = wxMeNuRepository.findAll();
        } else {
            //查询菜品
            foodList = wxMeNuRepository.findByCnameContaining(searchKey);
            log.info("搜索结果={}", foodList);
        }

        return zuZhuang(foodList);
    }

    public ResultVO zuZhuang(List<Menu> foodList) {
        List<Integer> categoryTypeList = foodList.stream()
                .map(e -> e.getMenutype())
                .collect(Collectors.toList());
        List<Catalog> leimuList = wxCatalogRepository.findByTypeIn(categoryTypeList);

        //3. 数据拼装
        List<LeimuVO> leimuVOList = new ArrayList<>();
        for (Catalog leimu : leimuList) {
            LeimuVO leimuVO = new LeimuVO();
            leimuVO.setLeimuType(leimu.getType());
            leimuVO.setLeimuName(leimu.getTypename());

            List<FoodRes> foodResList = new ArrayList<>();
            for (Menu food : foodList) {
                if (leimu.getType().equals(food.getMenutype())) {
                    FoodRes foodRes = new FoodRes();
                    BeanUtils.copyProperties(food, foodRes);
                    foodResList.add(foodRes);
                }
            }
            leimuVO.setFoodResList(foodResList);
            leimuVOList.add(leimuVO);
        }

        return ResultVOUtil.success(leimuVOList);
    }
}
