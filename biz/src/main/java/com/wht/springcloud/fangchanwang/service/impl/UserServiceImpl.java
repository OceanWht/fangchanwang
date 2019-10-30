package com.wht.springcloud.fangchanwang.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.model.UserModelExample;
import com.wht.springcloud.fangchanwang.service.FileService;
import com.wht.springcloud.fangchanwang.service.MailService;
import com.wht.springcloud.fangchanwang.service.UserService;
import com.wht.springcloud.fangchanwang.utils.BeanHelper;
import com.wht.springcloud.fangchanwang.utils.HashUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;


    /**
     * 文件服务器地址
     */
    @Value("${file.prefix}")
    private String imgPrefix;


    @Override
    public List<UserModel> getAllUsers() {
        return userModelMapper.selectByExample(new UserModelExample());
    }

    /**
     * 1.插入数据库，非激活
     * 2.密码加盐MD5
     * 3.保存头像到本地
     * 4.生成KEY 绑定email，用户点击激活连接可激活
     * 5.使用springbootmail发送邮件
     * 6.由于发送邮件比较慢，所以这个过程要是异步的
     *
     * @param userModel
     * @return
     * @Transactional 事务注解，发生异常会回滚，注意必须在别的类里面调用
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAccount(UserModel userModel) {
        //密码加盐
        String pwd = HashUtils.encryPwd(userModel.getPasswd());
        userModel.setPasswd(pwd);

        //设置图片地址
        List<String> imgList = fileService.getImgsPath(Lists.newArrayList(userModel.getAvatarFile()));
        if (!imgList.isEmpty()) {
            userModel.setAvatar(imgList.get(0));
        }
        //填充默认值
        //设置日期
        userModel.setCreateTime(new Date());
        BeanHelper.setDefaultProp(userModel, UserModel.class);
        BeanHelper.onInsert(userModel);
        //设置enable
        userModel.setEnable(false);

        //邮箱
        mailService.regiestNotify(userModel.getEmail());
        if (userModelMapper.insertSelective(userModel) != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean enable(String key) {
        return mailService.enable(key);
    }

    /**
     * 登录验证
     *
     * @param username
     * @param password
     */
    @Override
    public UserModel auth(String username, String password) {
        UserModelExample example = new UserModelExample();
        UserModelExample.Criteria criteria = example.createCriteria();
        String passwd = HashUtils.encryPwd(password);
        //根据用户密码且已激活 查询用户
        criteria.andNameEqualTo(username).andPasswdEqualTo(passwd).andEnableEqualTo(true);
        List<UserModel> userModelList = userModelMapper.selectByExample(example);
        //设置上传 图片的地址

        UserModel userModel = null;
        if (!CollectionUtils.isEmpty(userModelList) && userModelList.size() != 0) {
            userModelList.forEach(userModel1 -> {
                String avatar = userModel1.getAvatar().replaceAll("\\\\","/");
                userModel1.setAvatar(imgPrefix + userModel1.getAvatar());
            });
            userModel = userModelList.get(0);
        }
        return userModel;
    }

    @Override
    public void update(UserModel updateUser) {
        userModelMapper.updateByEmail(updateUser);
    }

    @Override
    public UserModel getUsersByquery(UserModel updateUser) {
        UserModelExample example = new UserModelExample();
        UserModelExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(updateUser.getEmail());
        List<UserModel> userModelList = userModelMapper.selectByExample(example);
        if (userModelList != null && userModelList.size() != 0){
            return userModelList.get(0);
        }

        return null;
    }


}
