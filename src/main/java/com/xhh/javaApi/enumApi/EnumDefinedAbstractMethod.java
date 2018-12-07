package com.xhh.javaApi.enumApi;

import org.junit.Test;

public class EnumDefinedAbstractMethod {
    @Test
    public void test1(){
        MyEnum1[] myEnums = {MyEnum1.Day1, MyEnum1.Day2};
        for (MyEnum1 e : myEnums){
            e.print();
        }
    }
}
enum MyEnum1{

    Day1("第一天"){
        public void print() {
            System.out.println("这是" + this.getDesc());
        }
    },
    Day2("第二天"){
        public void print() {
            System.out.println("这是" + this.getDesc());
        }
    };

    private String desc;//类描述

    public String getDesc() {
        return desc;
    }
    /**
     * 私有化构造，防止外部调用
     * @param desc
     */
    private MyEnum1(String desc){
        this.desc = desc;
    }

    public abstract void print();
}
