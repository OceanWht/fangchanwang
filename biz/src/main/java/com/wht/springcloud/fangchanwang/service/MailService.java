package com.wht.springcloud.fangchanwang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.wht.springcloud.fangchanwang.mapper.UserModelMapper;
import com.wht.springcloud.fangchanwang.model.UserModel;
import com.wht.springcloud.fangchanwang.model.UserModelExample;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserModelMapper userModelMapper;

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

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${domain.regiestCode}")
    private String regiestCode;

    @Value("${domain.name}")
    private String domainName;

    /**
     * 1.缓存KEY 与 Email的关系
     * 2.借助springmail 发送邮件
     * 3.使用异步框架进行异步操作,springboot默认引入异步框架，只要加上@Async注解
     * 4.异步方法所存在的类要和调用方法的类不能是同一个类
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
            sendMail("激活邮件",mailUrl,email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMail(String title, String mailUrl, String email) throws MessagingException {
        //构造邮件

        //构造SMTP邮件服务器的基本环境
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", true);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);


        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(Message.RecipientType.TO, email);//设置收信人
       // mimeMessage.addRecipients(Message.RecipientType.CC, "222@qq.com");//抄送
        mimeMessage.setFrom(email);//邮件发送人
        mimeMessage.setSubject(title);//邮件主题
        mimeMessage.setContent(mailUrl, "text/html;charset=utf-8");//正文

        //发送邮件
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", email, regiestCode);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());//发送邮件，第二个参数为收件人
        transport.close();
    }

    public boolean enable(String key) {
        String email = regiestCache.getIfPresent(key);
        if (StringUtils.isEmpty(email)){
            return false;
        }

        UserModel userModel = new UserModel();
        userModel.setEnable(true);
        userModel.setEmail(email);

        userModelMapper.updateByEmail(userModel);

        return true;
    }
}
