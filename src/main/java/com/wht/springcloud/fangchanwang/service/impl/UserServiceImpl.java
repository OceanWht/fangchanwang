package com.wht.springcloud.fangchanwang.service.impl;

import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.model.UserModelExample;
import com.wht.springcloud.fangchanwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;


    @Override
    public List<UserModel> getAllUsers() {
        return userModelMapper.selectByExample(new UserModelExample());
    }
}
