package com.project.patterndesignserver.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.patterndesignserver.model.member.User;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonStringUtil {
    public static <T> String toJsonString(T object){
        String res = JSONObject.fromObject(object).toString();
        return res;
    }

    public static <T> T getObjectFromJson(String json,Class c){
        T res = (T) JSONObject.toBean(JSONObject.fromObject(json),c);
        return res;
    }
    public static <T> List<T> getArrayFromJson(String json){
        Gson gson = new Gson();
        List<T> res = gson.fromJson(json, new TypeToken<List<T>>(){}.
                getType());
        return res;
    }
    public static <T> String getJsonFromArray(List<T> list){
        Gson gson = new Gson();
        String str = gson.toJson(list);
        return str;
    }
}
