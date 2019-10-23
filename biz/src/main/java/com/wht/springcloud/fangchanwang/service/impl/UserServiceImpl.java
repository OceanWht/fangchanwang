package com.wht.springcloud.fangchanwang.service.impl;

import com.google.common.collect.Lists;
import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.model.UserModelExample;
import com.wht.springcloud.fangchanwang.service.FileService;
import com.wht.springcloud.fangchanwang.service.UserService;
import com.wht.springcloud.fangchanwang.utils.BeanHelper;
import com.wht.springcloud.fangchanwang.utils.HashUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private FileService fileService;

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
     * @Transactional 事务注解，发生异常会回滚，注意必须在别的类里面调用
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAccount(UserModel userModel) {
        //密码加盐
        String pwd = HashUtils.encryPwd(userModel.getPasswd());
        userModel.setPasswd(pwd);

        //设置图片地址
        List<String> imgList = fileService.getImgsPath(Lists.newArrayList(userModel.getAvatarFile()));
        if (!imgList.isEmpty()){
            userModel.setAvatar(imgList.get(0));
        }
        //填充默认值
        //设置日期
        userModel.setCreateTime(new Date());
        BeanHelper.setDefaultProp(userModel,UserModel.class);
        BeanHelper.onInsert(userModel);
        //设置enable
        userModel.setEnable(false);

        //邮箱
        regiestNotify(userModel.getEmail());
        if (userModelMapper.insertSelective(userModel) != 0){
            return true;
        }else {
            return false;
        }
    }


    private void regiestNotify(String email) {
    }
}
