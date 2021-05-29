package com.project.patterndesignserver.member;

import com.project.patterndesignserver.service.email.AsynSendVerifyEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class EmailSendTest {
    @Autowired
    AsynSendVerifyEmailService asynSendVerifyEmailService;

    @Test
    public void send1(){
        asynSendVerifyEmailService.sendVerifyEmail("egoistvi@126.com");
    }
}
