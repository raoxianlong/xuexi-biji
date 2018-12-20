package com.xhh.javaApi.ReflectApi;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterDemo {


    /**
     *  获取参数名测试
     */
    @Test
    public void getParameterName() throws ClassNotFoundException, NoSuchMethodException {

        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.ParameterDemo");
        Method method = clazz.getMethod("testName", String.class, String.class, ParameterDemo.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter par : parameters){
            System.out.println(par.getName());
        }

    }

    public String testName(String name, String abc, ParameterDemo parameterDemo){
        return  null;
    }




}
