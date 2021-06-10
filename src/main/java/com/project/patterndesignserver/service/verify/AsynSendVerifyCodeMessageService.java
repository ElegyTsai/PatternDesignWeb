package com.project.patterndesignserver.service.verify;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.patterndesignserver.util.PhoneCodeUtil;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AsynSendVerifyCodeMessageService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Async
    public void sendCode(String phoneNumber){
        try {
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "109256"
                    , "dae77294-8902-405f-afb4-b1ceac059563" );
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phoneNumber );
            params.put("templateId", 5616);
            String[] templateParams = new String[2];
            String code = PhoneCodeUtil.getRandomCode();
            templateParams[0] = code; //模版👌
            templateParams[1] = "5";  //5分钟过期

            //设置缓存

            params.put("templateParams", templateParams);
            String result = client.send(params);
            //result: code:0 data:msg
            System.out.println(result);
            JsonParser jp = new JsonParser();
            JsonObject jo = jp.parse(result).getAsJsonObject();

            if(jo.get("code").getAsString().equals("0")){
                stringRedisTemplate.opsForValue().set("pho_"+phoneNumber,code,30, TimeUnit.MINUTES);
//                System.out.println("code:"+code);
                System.out.println("发送成功");
            }
            else{
                System.out.println("发送失败");
                System.out.println(jo.get("data").getAsString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean verifyCode(String phoneNumber,String code){
        String trueCode =stringRedisTemplate.opsForValue().get("pho_"+phoneNumber);
//        System.out.println(code+trueCode);
        if(code ==null){
            return false;
        }
        else if(code.equals(trueCode)){
            return true;
        }
        else return false;
    }



}
