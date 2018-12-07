package com.xhh.javaApi.ReflectApi;


import com.xhh.javaApi.utils.ReflectUtil;
import com.xhh.javaApi.utils.StringUtil;
import org.junit.Test;

import java.awt.font.FontRenderContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

public class FieldDemo {

    @Test
    public void getField() throws Exception{
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.Chinese");
        Field field = ReflectUtil.getFiled(clazz, "name");
        System.out.println(field.getName());
    }

    @Test
    public void setField() throws Exception{
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.Chinese");
        Object obj = clazz.newInstance();
        Field field = ReflectUtil.getFiled(clazz, "name");
        field.setAccessible(true);
        field.set(obj, "饶先龙");
        System.out.println("设置的值："+ obj);
        System.out.println("获取的值:" + field.get(obj));
    }

    @Test
    public void getType() throws Exception{
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.Chinese");
        String attr = "age";
        Integer value = 23;

        Object obj = clazz.newInstance();
        Field field = ReflectUtil.getFiled(clazz, attr);
        Method method = clazz.getMethod("set" + StringUtil.initcap(attr), field.getType());
        method.invoke(obj, value);
        System.out.println(obj);
    }
}

abstract class Person{

    private String name;
    private Integer age;
    public String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class Chinese extends  Person{

    private String skinColor;
    public String idCard;
    protected  String a;

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "Chinese{" +
                "email='" + email + '\'' +
                ", skinColor='" + skinColor + '\'' +
                ", idCard='" + idCard + '\'' +
                ", a='" + a + '\'' +
                ", name='" + getName() + '\'' +
                ", age='" + getAge() + '\'' +
                '}';
    }
}