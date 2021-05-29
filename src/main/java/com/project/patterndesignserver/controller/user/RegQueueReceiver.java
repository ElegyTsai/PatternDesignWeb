package com.project.patterndesignserver.controller.user;

import com.project.patterndesignserver.service.email.AsynSendVerifyEmailService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "reg.email")
public class RegQueueReceiver {
    @Autowired
    AsynSendVerifyEmailService asynSendVerifyEmailService;

    @RabbitHandler
    public void process(String email){
        try{
            System.out.println("Got it,send it later");
            asynSendVerifyEmailService.sendVerifyEmail(email);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
