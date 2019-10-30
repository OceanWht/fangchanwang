package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.model.HouseModel;
import com.wht.springcloud.fangchanwang.page.PageData;
import com.wht.springcloud.fangchanwang.page.PageParams;
import com.wht.springcloud.fangchanwang.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/house")
public class HouseController {

    @Autowired
    HouseService houseService;

    @RequestMapping(value = "/toAdd")
    public String toAdd(HttpServletRequest request){
        return "/house/add";
    }

    /**
     * 1.分页
     * 2.类型搜索
     * 3.排序
     * 4.基本信息展示  图片  价格 地址等信息
     * @return
     */
    @RequestMapping(value = "/list")
    public String houseList(Integer pageSize, Integer pageNum, HouseModel query, ModelMap modelMap){
        PageData<HouseModel> pageData = houseService.queryHouse(query, PageParams.build(pageSize,pageNum));
        return "house/listing";
    }
}
