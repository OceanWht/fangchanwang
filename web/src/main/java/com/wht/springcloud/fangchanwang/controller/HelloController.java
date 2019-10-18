package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String hello(ModelMap modelMap){
        List<UserModel> userModels = userService.getAllUsers();
        UserModel userModel = userModels.get(0);
        modelMap.put("user",userModel);
        return "hello";
    }


    @RequestMapping("/index")
    public String index(){
        return "homepage/index";
    }

}
