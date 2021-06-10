package com.project.patterndesignserver.util;

import java.util.UUID;

public class UUIDUtil {
    public static long getUUIDOfNumber(){
        long id = UUID.randomUUID().toString().hashCode();
        return id<0? -id:id;
    }
}
