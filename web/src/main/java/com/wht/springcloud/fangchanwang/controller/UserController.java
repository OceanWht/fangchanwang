package com.wht.springcloud.fangchanwang.controller;

import com.wht.springcloud.fangchanwang.common.result.ResultMsg;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.service.AgencyService;
import com.wht.springcloud.fangchanwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AgencyService agencyService;

    /**
     * 注册提交：1.注册验证，2.发送邮件 3.验证失败重定向到注册页面
     * 注册获取：根据usermodel对象的用户密码是否为空
     * @param userModel
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accounts/register")
    public String register(UserModel userModel, ModelMap modelMap){
        if (StringUtils.isEmpty(userModel.getName()) && StringUtils.isEmpty(userModel.getPasswd())){
            return "user/accounts/register";
        }

        //用户验证
        ResultMsg resultMsg = UserHelper.validate(userModel);
        if (resultMsg.isSuccess() && userService.addAccount(userModel)){
            //返回注册成功页
            return "user/accounts/registerSubmit";
        }else {
          //  modelMap.put("errorMsg",resultMsg.getErrorMsg());
            return "redirect: user/accounts/register?"+resultMsg.asUrlParams();
        }
    }

    @RequestMapping(value = "/accounts/verify")
    @ResponseBody
    public String verify(String key){
        userService.verify(key);
        return key;
    }

}
