package com.xhh.javaApi.utils;

/**
 * String工具类
 */
public class StringUtil {

    /**
     * 首字母大写
     */
    public static String initcap(String str){
        if (str == null || str.trim().equals("")){
            return str;
        }else{
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    public static boolean isNotBlank(String string){
        if (string == null){
            return false;
        }
        if (string.trim().equals("")){
            return false;
        }
        return true;
    }

}
