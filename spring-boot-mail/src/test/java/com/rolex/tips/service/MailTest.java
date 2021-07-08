package com.rolex.tips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.UUID;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MailTest {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    public String from;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test1() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(new String[]{"zengronghui@kingsoft.com"});
        message.setFrom(from);
        message.setSubject("简单邮件");
        message.setText("这是一封测试邮件");
        mailSender.send(message);
    }

    @Test
    public void test2() throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from, "自定义昵称");
        helper.setTo(new String[]{"zengronghui@kingsoft.com"});
        helper.setSubject("带图片和附件的HTML格式邮件");
        helper.setText("<html><body><h1>这是一封测试邮件</h1><br><img src='cid:github'/></body></html>", true);
        // 发送图片
        File file = ResourceUtils.getFile("classpath:static" + File.separator + "github.jpg");
        helper.addInline("github", file);
        // 发送附件
        file = ResourceUtils.getFile("classpath:static" + File.separator + "github.jpg");
        helper.addAttachment("github.jpg", file);
        mailSender.send(message);
    }

    @Test
    public void test3() throws Exception {
        Context context = new Context();
        context.setVariable("id", UUID.randomUUID().toString());
        String emailContent = templateEngine.process("template", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(new String[]{"zengronghui@kingsoft.com"});
        helper.setSubject("使用模板的邮件");
        helper.setText(emailContent, true);
        mailSender.send(message);
    }
}
