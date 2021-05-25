package com.project.patterndesignserver.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {
    public static String serializeToString(Object obj) throws Exception{
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(obj);
        String str = byteOut.toString("ISO-8859-1");
        return str;
    }
}
