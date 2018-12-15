package utils;

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

}
