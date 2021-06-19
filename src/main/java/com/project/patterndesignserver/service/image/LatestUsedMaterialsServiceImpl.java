package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.result.ExceptionMsg;
import com.project.patterndesignserver.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class LatestUsedMaterialsServiceImpl implements LatestUsedMaterialsService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<String> add(String url) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(userId);
        Result<String> res;
        if(userId.equals("anonymousUser")){
            res = new Result<>(ExceptionMsg.UserNotAuthenticated);
            res.setData("用户未登陆，记录失败");
            return res;
        }
        String redisKey = "mLog_"+userId;
        stringRedisTemplate.opsForZSet().add(redisKey,url, TimeUtil.getCurrentTime() *-1.0);
        int maxLog = 100; // 缓存100个
        int showLog = 50;
        if(stringRedisTemplate.opsForZSet().size(redisKey)==maxLog){
            stringRedisTemplate.opsForZSet().removeRange(redisKey,50,-1);
        }
        res = new Result<>();
        res.setData("添加成功");
        return res;
    }

    @Override
    public Result<List> query(int rank) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(userId);
        Result<List> res;
        List<String> data = new ArrayList<String>() ;
        if(userId.equals("anonymousUser")){
            res = new Result<>(ExceptionMsg.UserNotAuthenticated);
            return res;
        }
        String redisKey = "mLog_"+userId;
        if(stringRedisTemplate.opsForZSet().size(redisKey)==0){
            res = new Result<>();
            res.setMsg("无数据");
        }
        Set<String> queryResult;
        if(rank>stringRedisTemplate.opsForZSet().size(redisKey)){
            queryResult = stringRedisTemplate.opsForZSet().range(redisKey,0,-1);
        }else{
            queryResult = stringRedisTemplate.opsForZSet().range(redisKey,0,rank-1);
        }
        for(String s : queryResult){
            data.add(s);
        }
        res = new Result<>();
        res.setData(data);
        return res;
    }
}
