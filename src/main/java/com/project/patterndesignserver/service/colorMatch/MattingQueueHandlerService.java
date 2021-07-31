package com.project.patterndesignserver.service.colorMatch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class MattingQueueHandlerService {
    @Autowired
    MattingService mattingService;

    @RabbitListener(queues = "matting.receiver")
    @RabbitHandler
    public void updateFeedback(String msg){

        try {
            System.out.println("get msg" + msg.substring(0,10));
            mattingService.updateState(msg);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
