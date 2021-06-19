package com.project.patterndesignserver.util;

import com.project.patterndesignserver.model.member.User;
import net.sf.json.JSONObject;

public class JsonStringUtil {
    public static <T> String toJsonString(T object){
        String res = JSONObject.fromObject(object).toString();
        return res;
    }

    public static <T> T getObjectFromJson(String json,Class c){

        T res = (T) JSONObject.toBean(JSONObject.fromObject(json),c);
        return res;
    }
}
