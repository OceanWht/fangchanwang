package com.wht.springcloud.fangchanwang.service.impl;

import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.model.UserModelExample;
import com.wht.springcloud.fangchanwang.service.UserService;
import com.wht.springcloud.fangchanwang.utils.HashUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;


    @Override
    public List<UserModel> getAllUsers() {
        return userModelMapper.selectByExample(new UserModelExample());
    }

    /**
     * 1.插入数据库，非激活
     * 2.密码加盐MD5
     * 3.保存头像到本地
     * 4.生成KEY 绑定email，用户点击激活连接可激活
     * @param userModel
     * @return
     */
    @Override
    public boolean addAccount(UserModel userModel) {
        //密码加盐
        String pwd = HashUtils.encryPwd(userModel.getPasswd());
        userModel.setPasswd(pwd);
        userModel.setCreateTime(new Date());
        if (userModelMapper.insertSelective(userModel) != 0){
            return true;
        }else {
            return false;
        }
    }
}
