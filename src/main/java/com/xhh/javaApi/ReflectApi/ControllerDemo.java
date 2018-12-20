package com.xhh.javaApi.ReflectApi;

import com.xhh.javaApi.utils.BeanHandle;

public class ControllerDemo {


    public static void main(String[] args) {
        Emp emp = new Emp();

        String paramName = "emp.dep.company.name";
        String value = "鑫合汇";
        new BeanHandle(emp, paramName ,value);

        String paramName2= "emp.df";
        String value2 = "2018-12-12";
        new BeanHandle(emp, paramName2 ,value2);

        String paramName3 = "emp.dep.empNames";
        String[] value3 = {"饶先龙", "张三", "李四"};
        new BeanHandle(emp, paramName3 ,value3);

        String paramName4 = "emp.dep.company.peopleNums";
        String value4 = "888";
        new BeanHandle(emp, paramName4 ,value4);

        String paramName5 = "emp.company.name";
        String value5 = "鑫合汇";
        new BeanHandle(emp, paramName5 ,value5);


        System.out.println(emp);
    }


}
