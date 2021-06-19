package com.project.patterndesignserver.module.task;

import com.project.patterndesignserver.mapper.content.material.MaterialLogMapper;
import com.project.patterndesignserver.model.content.material.MaterialLog;
import com.project.patterndesignserver.util.redis.MaterialLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MaterialLogSaveScheduledTask {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MaterialLogMapper materialLogMapper;

    @Scheduled(cron = "00 0/3 * * * ?")
    //设置的意思是 每3分钟触发一次，便于测试，实际上线是可以设置成每一周触发一次（根据访问人数
    public void syncDatabase(){
        MaterialLogUtil.cleanCache(stringRedisTemplate,materialLogMapper);
    }
}
