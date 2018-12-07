package com.xhh.javaApi.enumApi;

import org.junit.Test;

public class EnumDefinedMethod {
    @Test
    public void test1(){
        // Day1 - Day7 实际上是MyEnum的一个实例对象
        MyEnum[] myEnums = {MyEnum.Day1, MyEnum.Day2,MyEnum.Day3,
                                MyEnum.Day4, MyEnum.Day5, MyEnum.Day6,MyEnum.Day7};
        for (MyEnum e : myEnums){
            System.out.println(e.getDesc());
        }
    }
}
enum MyEnum{

    Day1("第一天"),
    Day2("第二天"),
    Day3("第三天"),
    Day4("第四天"),
    Day5("第五天"),
    Day6("第六天"),
    Day7("第七天");

    private String desc;//类描述

    public String getDesc() {
        return desc;
    }
    /**
     * 私有化构造，防止外部调用
     * @param desc
     */
    private MyEnum(String desc){
        this.desc = desc;
    }
}
