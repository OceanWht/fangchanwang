package com.wht.springcloud.fangchanwang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendMail(String title, String mailUrl, String email) throws MessagingException {
        //构造邮件
     /*   SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailUsername);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(mailUrl);
        mailSender.send(simpleMailMessage);
*/

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
        transport.connect("smtp.qq.com", email, "vuihiyaqklvvcajg");
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());//发送邮件，第二个参数为收件人
        transport.close();
    }
}
