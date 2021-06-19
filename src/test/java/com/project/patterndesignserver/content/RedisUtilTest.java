package com.project.patterndesignserver.content;

import com.project.patterndesignserver.mapper.content.material.MaterialLogMapper;
import com.project.patterndesignserver.util.redis.MaterialLogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisUtilTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MaterialLogMapper materialLogMapper;
    @Test
    public void test1() {
        System.out.println(MaterialLogUtil.isInCache("123", stringRedisTemplate));
    }
    @Test
    public void test2() {
        System.out.println(MaterialLogUtil.isInCache(1+"",stringRedisTemplate));
    }
    @Test
    public void test3() {
        MaterialLogUtil.readToCache(2+"",stringRedisTemplate,materialLogMapper);
    }
    @Test
    public void test4() {
        List<String> urls = MaterialLogUtil.readFromCache(1+"",50,stringRedisTemplate);
        for(String s :urls) {
            System.out.println(stringRedisTemplate.opsForZSet().score("mLog_"+1,s).longValue());
            System.out.println(s);
        }
    }
    @Test
    public void test5() {
        MaterialLogUtil.addToCache(""+1,"/test5/user1/1",stringRedisTemplate);
    }
    @Test
    public void test6(){
        MaterialLogUtil.writeToSQL(""+1,stringRedisTemplate,materialLogMapper);
    }

    @Test
    public void test7(){
        System.out.println(stringRedisTemplate.opsForZSet().size("materialLogCache"));
//        MaterialLogUtil.cleanCache(stringRedisTemplate,materialLogMapper);
        System.out.println(stringRedisTemplate.opsForZSet().size("materialLogCache"));
//        MaterialLogUtil.writeToSQL(""+1,stringRedisTemplate,materialLogMapper);
    }
}
