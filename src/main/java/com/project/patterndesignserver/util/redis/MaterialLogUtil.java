package com.project.patterndesignserver.util.redis;

import com.project.patterndesignserver.mapper.content.material.MaterialLogMapper;
import com.project.patterndesignserver.model.content.material.MaterialLog;
import com.project.patterndesignserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class MaterialLogUtil {


    public static boolean isInCache(String userId,StringRedisTemplate stringRedisTemplate){
//        if(stringRedisTemplate.opsForZSet().)
        return stringRedisTemplate.opsForZSet().score("materialLogCache",userId) != null ;
    }

    public static void addToCache(String userId,String url,StringRedisTemplate stringRedisTemplate){
        String redisKey = "mLog_"+userId;
        stringRedisTemplate.opsForZSet().add(redisKey,url, TimeUtil.getCurrentTime()*-1.0);
        int maxLog = 100; // 缓存100个

        if(stringRedisTemplate.opsForZSet().size(redisKey)>=maxLog){
            stringRedisTemplate.opsForZSet().removeRange(redisKey,50,-1);
        }
        stringRedisTemplate.opsForZSet().add("materialLogCache",userId,System.currentTimeMillis());//刷新修改时间
    }
    public static List<String> readFromCache(String userId,int rank,StringRedisTemplate stringRedisTemplate){
        String redisKey = "mLog_"+userId;
        Set<String> queryResult;
        if(rank>stringRedisTemplate.opsForZSet().size(redisKey)){
            queryResult = stringRedisTemplate.opsForZSet().range(redisKey,0,-1);
        }else{
            queryResult = stringRedisTemplate.opsForZSet().range(redisKey,0,rank-1);
        }
        LinkedList<String> ret = new LinkedList<>();
        for(String url :queryResult){
            ret.addLast(url);
        }
        return ret;
    }

    public static void readToCache(String userId, StringRedisTemplate stringRedisTemplate, MaterialLogMapper materialLogMapper){
        List<MaterialLog> logs = materialLogMapper.queryByUserId(Long.parseLong(userId));
        String redisUserKey = "mLog_"+userId;
        for(MaterialLog log : logs){
            stringRedisTemplate.opsForZSet().add(redisUserKey, log.getUrl(), log.getTimeOfLastUsing()*-1.0);
        }
        stringRedisTemplate.opsForZSet().add("materialLogCache",userId,System.currentTimeMillis());
    }

    public static void writeToSQL(String userId, StringRedisTemplate stringRedisTemplate, MaterialLogMapper materialLogMapper){
        materialLogMapper.deleteByUserId(Long.parseLong(userId));
        List<String> urlCaches = readFromCache(userId,50,stringRedisTemplate);
        String redisUserKey = "mLog_"+userId;
        for(String url:urlCaches){
            MaterialLog log = new MaterialLog();
            log.setTimeOfLastUsing((stringRedisTemplate.opsForZSet().score(redisUserKey,url)).longValue()*-1);
            log.setUrl(url);
            log.setUser_Id(Long.parseLong(userId));
            materialLogMapper.insertLog(log);
        }
        stringRedisTemplate.opsForZSet().reverseRange(redisUserKey,0,-1);
        stringRedisTemplate.opsForZSet().remove("materialLogCache",userId);
    }

    public static void cleanCache(StringRedisTemplate stringRedisTemplate,MaterialLogMapper materialLogMapper){
//        long capacity = 10000;
        //一万个用户的缓存
        long capacity = 1; //for test
        if(stringRedisTemplate.opsForZSet().size("materialLogCache")>=capacity){
            Set<String> outUser = stringRedisTemplate.opsForZSet().range("materialLogCache",0,stringRedisTemplate.opsForZSet().size("materialLogCache")-capacity);
            for(String userId : outUser){
                writeToSQL(userId,stringRedisTemplate,materialLogMapper);
            }
        }
        Set<String> outUser = stringRedisTemplate.opsForZSet().rangeByScore("materialLogCache",0,System.currentTimeMillis()-(1000*60*60*24*3));//3天过期
        for(String userId : outUser){
            writeToSQL(userId,stringRedisTemplate,materialLogMapper);
        }
    }
}
