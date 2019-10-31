package com.wht.springcloud.fangchanwang.service.impl;

import com.wht.springcloud.fangchanwang.mapper.AgencyModelMapper;
import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.service.AgencyService;
import com.wht.springcloud.fangchanwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AgencyServiceImpl implements AgencyService {


    @Autowired
    UserService userService;

    @Autowired
    UserModelMapper userModelMapper;

    @Autowired
    AgencyModelMapper agencyModelMapper;

    @Value("${file.prefix}")
    String prefix;

    /**
     * 访问User获取详情
     * 添加用户头像
     * @param userId
     * @return
     */
    @Override
    public UserModel getAgnetDetail(Long userId) {

        UserModel userModel = new UserModel();
        userModel.setId(userId);
        //代表经纪人，数据库中为0，1为个人
        userModel.setType(false);
        UserModel user= userModelMapper.selectByPrimaryKey(userId);

        return user;
    }
}
