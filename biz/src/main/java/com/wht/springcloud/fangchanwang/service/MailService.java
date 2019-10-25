package com.wht.springcloud.fangchanwang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendMail(String title, String mailUrl, String email) {
        //构造邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailUsername);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(mailUrl);
        mailSender.send(simpleMailMessage);
    }
}
