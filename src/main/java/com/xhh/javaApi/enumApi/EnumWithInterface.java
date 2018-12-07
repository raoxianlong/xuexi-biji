package com.xhh.javaApi.enumApi;

interface Foot{
    //踢
    public void kick();
}

interface head{
    //撞
    public void strike();

}

public enum EnumWithInterface implements Foot, head{

    Day1,Day2;//必须以分号结束

    public void kick() {
        System.out.println("向前踢");
    }

    public void strike() {
        System.out.println("向前撞");
    }

    public static void main(String[] args) {
        EnumWithInterface.Day1.kick();
        EnumWithInterface.Day1.strike();
    }
}
