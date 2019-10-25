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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    /**
     * gugva本地缓存，定义了最大注册数，超时时间为15分钟，如果超时还未注册，需要删除数据库重该用户
     */
    private final Cache<String,String> regiestCache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES)
            .maximumSize(100).removalListener(new RemovalListener<String, String>() {
                /**
                 * 如果超过15分钟未注册就删除该用户
                 * @param notification
                 */
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                         UserModelExample example = new UserModelExample();
                         UserModelExample.Criteria criteria = example.createCriteria();
                         criteria.andEmailEqualTo(notification.getValue());
                         userModelMapper.deleteByExample(example);
                }
            }).build();

    @Autowired
    private UserModelMapper userModelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Value("${domain.name}")
    private String domainName;



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

    /**
     * 1.缓存KEY 与 Email的关系
     * 2.借助springmail 发送邮件
     * 3.使用异步框架进行异步操作,springboot默认引入异步框架，只要加上@Async注解
     * @param email
     */
    @Async
    public void regiestNotify(String email) {
        //生成KEY,随机10位字符串
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        //放入本地缓存,将KEY 与Email绑定映射并存入本地，用guavacache存入
        regiestCache.put(randomKey,email);
        //先构造邮件地址
        String mailUrl = "http://"+domainName+"/accounts/verify?key="+randomKey;
        //发送邮件，引入spring mail pom文件
        try {
            mailService.sendMail("激活邮件",mailUrl,email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
