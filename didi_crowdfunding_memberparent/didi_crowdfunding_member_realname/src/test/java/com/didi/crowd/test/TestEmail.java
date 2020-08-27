package com.didi.crowd.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEmail {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public  void sendHtmlMail() throws Exception {
        String receiver="test@didi.com";//接收者账户
        String postCount="admin@didi.com";//发送者账户
        String code="1231";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setFrom(postCount);
        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【HTML】");//邮件主题
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head>");
        sb.append("<body><h1>实名认证专用</h1><p>【didi众筹科技】您的验证码是：")
                .append(code)
                .append("，10分钟内有效，请勿将验证码透露给他人，谨防诈骗。</p></body>");
        sb.append("</html>");
        // 启用html
        mimeMessageHelper.setText(sb.toString(), true);
        // 发送邮件
        mailSender.send(mimeMessage);
        System.out.println("发送成功");
    }
}
