package com.project.patterndesignserver.controller.user;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "reg.email")
public class RegQueueReceiver {
    @RabbitHandler
    public void process(String email){
        try{
            System.out.println("Got it,send it later");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
