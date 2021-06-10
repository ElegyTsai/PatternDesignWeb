package com.project.patterndesignserver.util;

public class PhoneCodeUtil {
    public static String getRandomCode(){
        int code = (int)(Math.random()*1000000);
        return String.format("%06d",code);
    }
}
