package com.project.patterndesignserver.member;

import com.project.patterndesignserver.service.verify.AsynSendVerifyCodeMessageService;
import com.project.patterndesignserver.util.PhoneCodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PhoneCodeTest {
    @Autowired
    AsynSendVerifyCodeMessageService sendVerifyCodeMessageService;
    @Test
    public void sendCode(){
        sendVerifyCodeMessageService.sendCode("18868104441");
    }
    @Test
    public void getRandomCode(){
        System.out.println(PhoneCodeUtil.getRandomCode());
    }
}
