package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public List<UserModel> users() {

        List<UserModel> userModels = userService.getAllUsers();
        return userModels;
    }
}
