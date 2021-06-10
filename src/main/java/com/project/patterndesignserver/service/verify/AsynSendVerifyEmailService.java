package com.project.patterndesignserver.service.verify;

import com.project.patterndesignserver.mapper.member.UserMapper;
import com.project.patterndesignserver.util.MD5Util;
import com.project.patterndesignserver.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AsynSendVerifyEmailService {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${mail.subject.activeSubject}")
    private String mailSubject;
    @Value("${mail.activeuserUrl}")
    private String activeuserUrl;
    @Value("${mail.content.active}")
    private String mailActivateContent;

    @Async
    public void sendVerifyEmail(String email) {
        try {
            String secretKey = UUID.randomUUID().toString(); //生成密匙
            Timestamp outDate = new Timestamp(System.currentTimeMillis() + (30 * 60 * 1000));//30*60*1000表示30分钟
            long date = outDate.getTime() / 1000 * 1000;
            userMapper.setValidationAndOutDateByEmail(secretKey, outDate + "", email);
            String key = email + "$" + date + "$" + secretKey;
            String digitalSignature = MD5Util.encode(key);  //数字签名
            String resetPassHref = activeuserUrl + "?sid="
                    + digitalSignature + "&email=" + email;
            String emailContent = MessageUtil.getMessage(mailActivateContent, resetPassHref);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailFrom);
            System.out.println(mailFrom);
            helper.setTo(email);
            helper.setSubject(mailSubject);
            helper.setText(emailContent, true);

            mailSender.send(mimeMessage);
            System.out.println("sent email to "+email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
