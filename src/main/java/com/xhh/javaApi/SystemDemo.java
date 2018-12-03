package com.xhh.javaApi;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class SystemDemo {

    /**
     * 测试获取系统当前时间的方法
     */
    @Test
    public void testCurrentTimeMillis(){
        long start = System.currentTimeMillis();
        //耗时操作
        String str = "";
        for (int x = 0 ; x < 50000; x++){
            str += x;
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时 ：" + (end - start) + " ms");
    }


    /**
     * 测试更改系统输出地址
     */
    @Test
    public void testSetOut() throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src\\log.log"));
        System.setOut(ps);
        System.out.println("我是猪");
        System.out.println("我是猪");
        System.out.println("我是猪");
        System.out.println("我是猪");
    }

    /**
     * 测试获取系统属性
     */
    @Test
    public void tsetGetProperties(){
        Properties properties = System.getProperties();
        properties.list(System.out);
    }



}
