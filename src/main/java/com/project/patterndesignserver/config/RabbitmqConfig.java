package com.project.patterndesignserver.config;



import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue regQueue() { return new Queue("reg.email");}

    @Bean
    public Queue regCodeQueue(){ return new Queue("reg.phone");}

}
