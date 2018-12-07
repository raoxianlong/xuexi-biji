package com.xhh.javaApi.enumApi;

import org.junit.Test;

public class EnumPrinciple{
   /* public static void main(String[] args) {
        EnumPrinciple[] enumPrinciples =
                {EnumPrinciple.DAY1,EnumPrinciple.DAY2,EnumPrinciple.DAY3};

        for (EnumPrinciple e : enumPrinciples){
            System.out.println(e.name());
            System.out.println(e.ordinal());
        }
    }*/

    /**
     * 枚举与Class
     */
    @Test
    public void testEnumWithClass(){
        //等同于getClass()一样
        Class<Day> eClass = Day.DAY1.getDeclaringClass();
        System.out.println("是否是枚举类型:" + eClass.isEnum());
        Day[] es = eClass.getEnumConstants();//返回枚举中的所有实例对象
        for (Day enumPrinciple : es){
            System.out.println(enumPrinciple.name());
            System.out.println(enumPrinciple.ordinal());
        }
    }
}

/**
 * 枚举原理练习
 */
enum Day {
    DAY1, DAY2, DAY3
}


