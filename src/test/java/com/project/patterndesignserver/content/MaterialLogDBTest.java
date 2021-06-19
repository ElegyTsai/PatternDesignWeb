package com.project.patterndesignserver.content;

import com.project.patterndesignserver.mapper.content.material.MaterialLogMapper;
import com.project.patterndesignserver.model.content.material.MaterialLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MaterialLogDBTest {
    @Autowired
    MaterialLogMapper materialLogMapper;

    @Test
    public void add(){
        List<MaterialLog> logs = new ArrayList<>();
        for(int i = 0;i<10;i++){
            MaterialLog log = new MaterialLog();
            log.setUser_Id(1);
            log.setUrl("/test/user"+i);
            log.setTimeOfLastUsing(System.currentTimeMillis());
            materialLogMapper.insertLog(log);
            logs.add(log);
        }

    }
    @Test
    public void delete(){
        materialLogMapper.deleteByUserId(0);
    }
    @Test
    public void query(){
        List<MaterialLog> logs = materialLogMapper.queryByUserId(1);
        System.out.println(logs.size());
    }
}
