package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.autoconfig.AutoConfigDemo;
import com.wht.springcloud.fangchanwang.autoconfig.AutoConfigDemoClass;
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

    /**
     * 自动注解例子
     */
    @Autowired
    AutoConfigDemo autoConfigDemo;

    @RequestMapping("/hello")
    public String hello(ModelMap modelMap){
        List<UserModel> userModels = userService.getAllUsers();
        UserModel userModel = userModels.get(0);
        AutoConfigDemoClass configDemoClass = autoConfigDemo.autoConfigDemoClass();
        /* if (userModel != null){
            throw new IllegalArgumentException();
        }*/
        modelMap.put("user",userModel);
        return "hello";
    }


    @RequestMapping("/index")
    public String index(){
        return "homepage/index";
    }

}
