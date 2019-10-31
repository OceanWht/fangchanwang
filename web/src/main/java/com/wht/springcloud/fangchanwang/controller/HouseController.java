package com.wht.springcloud.fangchanwang.controller;

import com.google.common.base.Strings;
import com.wht.springcloud.fangchanwang.model.HouseModel;
import com.wht.springcloud.fangchanwang.page.PageData;
import com.wht.springcloud.fangchanwang.page.PageParams;
import com.wht.springcloud.fangchanwang.service.AgencyService;
import com.wht.springcloud.fangchanwang.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/house")
public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    AgencyService agencyService;

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

        modelMap.put("ps",pageData);
        modelMap.put("vo",query);
        return "house/listing";
    }

    /**
     * 查询房屋详情
     * 查询关联经纪人
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request,ModelMap modelMap){
        String id = request.getParameter("id");
        if (!Strings.isNullOrEmpty(id)){
           HouseModel house =  houseService.queryHouseById(id);
           if (house.getUserId() != null && !house.getUserId().equals(0)){
                modelMap.put("agent",agencyService.getAgnetDetail(house.getUserId()));
           }
           modelMap.put("house",house);
        }

        return "/house/detail";
    }
}
