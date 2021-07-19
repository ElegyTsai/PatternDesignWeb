package com.project.patterndesignserver.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue regQueue() { return new Queue("reg.email");}

    @Bean
    public Queue regCodeQueue(){ return new Queue("reg.phone");}


    //以下定义topic的exchange订阅模式
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("mattingExchange");
    }
    @Bean
    public Queue HandlerQueue1(){ return new Queue("matting.handler.1");}

    @Bean
    public Queue HandlerQueue2(){return new Queue("matting.handler.2");}

    @Bean
    Binding bindingHandlerQueue1(Queue HandlerQueue1, TopicExchange exchange){
        return BindingBuilder.bind(HandlerQueue1).to(exchange).with("handler.1");
    }
    @Bean
    Binding bindingHandlerQueue2(Queue HandlerQueue2, TopicExchange exchange){
        return BindingBuilder.bind(HandlerQueue2).to(exchange).with("handler.2");
    }
    @Bean
    public Queue ResultReceiveQueue(){return new Queue("matting.receiver");}

}
