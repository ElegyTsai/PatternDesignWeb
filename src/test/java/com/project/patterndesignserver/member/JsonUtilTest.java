package com.project.patterndesignserver.member;


import com.project.patterndesignserver.model.content.image.PublicImageResult;
import com.project.patterndesignserver.model.member.User;
import com.project.patterndesignserver.util.JsonStringUtil;
import net.sf.json.JSONObject;
import org.junit.Test;

public class JsonUtilTest {
    @Test
    public void test1(){
        PublicImageResult object = new PublicImageResult();
        object.setUUID("123123123");
//        String res = JSONObject.fromObject(user1).toString();
        String json =  JsonStringUtil.<PublicImageResult>toJsonString(object);
        PublicImageResult object2 = JsonStringUtil.getObjectFromJson(json,PublicImageResult.class);
        System.out.println(json);
    }
    @Test
    public void test2(){
        PublicImageResult object = new PublicImageResult();
        object.setUUID("123123123");
//        String res = JSONObject.fromObject(user1).toString();
        String json =  JsonStringUtil.<PublicImageResult>toJsonString(object);
        PublicImageResult object2 = JsonStringUtil.getObjectFromJson(json,PublicImageResult.class);
        System.out.println(json);
    }
}
