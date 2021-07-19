package com.project.patterndesignserver.service.colorMatch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "matting.receiver")
public class MattingQueueHandlerService {
    @Autowired
    MattingService mattingService;
    @RabbitHandler
    public void updateFeedback(String msg){
        mattingService.updateState(msg);
    }
}
