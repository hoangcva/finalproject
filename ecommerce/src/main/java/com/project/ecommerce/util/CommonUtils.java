package com.project.ecommerce.util;

public class CommonUtils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.equals("");
    }

    public static String nullToString(Object object) {
        String retStr = "";
        if (object != null) {
            retStr = object.toString();
        }
        return retStr;
    }
}
