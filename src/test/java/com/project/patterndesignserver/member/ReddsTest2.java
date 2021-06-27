package com.project.patterndesignserver.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReddsTest2 {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1(){
        redisTemplate.opsForValue().set("key","value1");

    }
    @Test
    public void test2(){
        Object s =redisTemplate.opsForValue().get("key");
        System.out.println(s);
    }

    @Test
    public void test3(){
        Double a = new Double(2);
        Double b = new Double(2.0);
        System.out.println(Double.hashCode(a));
        System.out.println(Double.hashCode(b));
    }
}
