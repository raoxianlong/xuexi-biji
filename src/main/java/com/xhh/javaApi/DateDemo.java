package com.xhh.javaApi;

import java.util.Date;

public class DateDemo {

    public static void main(String[] args) {

        //获取当前日期
        Date date = new Date();
        System.out.println(date);

        //Long类型和时间类的转换
        Long time = System.currentTimeMillis();
        System.out.println(new Date(time));

    }

}
