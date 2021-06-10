package com.project.patterndesignserver.controller.user;

import com.project.patterndesignserver.service.verify.AsynSendVerifyCodeMessageService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "reg.phone")
public class RegPhoneReceiver {
    @Autowired
    AsynSendVerifyCodeMessageService asynSendVerifyCodeMessageService;
    @RabbitHandler
    public void sendMessage(String phoneNumber){
        asynSendVerifyCodeMessageService.sendCode(phoneNumber);
    }
}
