package com.project.patterndesignserver.service.image;

import com.project.patterndesignserver.mapper.content.material.MaterialLogMapper;
import com.project.patterndesignserver.util.TimeUtil;
import com.project.patterndesignserver.util.redis.MaterialLogUtil;
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

    @Autowired
    MaterialLogMapper materialLogMapper;
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
        if(!MaterialLogUtil.isInCache(userId,stringRedisTemplate)){
            MaterialLogUtil.readToCache(userId,stringRedisTemplate,materialLogMapper);
        }
        MaterialLogUtil.addToCache(userId,url,stringRedisTemplate);
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
        if(!MaterialLogUtil.isInCache(userId,stringRedisTemplate)){
            MaterialLogUtil.readToCache(userId,stringRedisTemplate,materialLogMapper);
        }
        List<String> queryResult;
        queryResult = MaterialLogUtil.readFromCache(userId,rank,stringRedisTemplate);
        res = new Result<>();
        res.setData(queryResult);
        return res;
    }
}
