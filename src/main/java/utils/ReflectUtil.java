package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 */
public class ReflectUtil {


    /**
     *  查找字段
     * @param clazz 需要查找字段的所有类.class
     * @param fieldName 字段名
     * @return  找到返回Field，没找到返回Null
     */
    public static Field getFiled(Class<?> clazz, String fieldName) {
        while (clazz != null){
            try {
                Field field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    /**
     *  设置字段值
     * @param fieldName
     */
    public static void setValue(Object obj, String fieldName, Object value){
        try {
            Class<?> clazz = obj.getClass();
            Field field = getFiled(clazz, fieldName);
            Method method = clazz.getMethod("set" + StringUtil.initcap(fieldName), field.getType());
            method.invoke(obj, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取置字段值
     * @param fieldName
     */
    public static Object getValue(Object obj, String fieldName){
        try {
            Class<?> clazz = obj.getClass();
            Field field = getFiled(clazz, fieldName);
            Method method = clazz.getMethod("get" + StringUtil.initcap(fieldName));
            return  method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
