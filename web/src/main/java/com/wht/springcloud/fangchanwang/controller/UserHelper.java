package com.wht.springcloud.fangchanwang.controller;

import com.google.common.base.Objects;
import com.wht.springcloud.fangchanwang.common.result.ResultMsg;
import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UserHelper {

    public static ResultMsg validate(UserModel account){
        if (StringUtils.isEmpty(account.getEmail())){
            return ResultMsg.errorMsg("Email有误");
        }
        if (StringUtils.isEmpty(account.getName())){
            return ResultMsg.errorMsg("名字有误");
        }
        if (StringUtils.isEmpty(account.getConfirmPassword()) || StringUtils.isEmpty(account.getPasswd()) ||
         !account.getPasswd().equals(account.getConfirmPassword())){
            return ResultMsg.errorMsg("密码有误");
        }

        if (account.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码小于6位");
        }

        return ResultMsg.successMsg("");
    }

    public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)) {
            return ResultMsg.errorMsg("参数有误");
        }
        if (!Objects.equal(password, confirmPassword)) {
            return ResultMsg.errorMsg("密码必须与确认密码一致");
        }
        return ResultMsg.successMsg("");
    }
}
