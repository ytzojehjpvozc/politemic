package com.xbh.politemic.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Description: 邮件发送
 * @Author: zhengbohang
 * @Date: 2021/10/4 22:35
 */
@Slf4j
@Component
public class MailClient {

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromUser;

    /**
     * 发送邮件
     * @author: zhengbohang
     * @date: 2021/10/4 22:44
     */
    public void sendEmail(String toUser, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(fromUser);
        helper.setTo(toUser);
        helper.setSubject(subject);
        helper.setText(content, true);
        sender.send(helper.getMimeMessage());
    }
}
