package com.xhh.javaApi.bio;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputStreamDemo {


    @Test
    public void test() throws Exception {
        // 1.通过File定义一个文件资源
        File file = new File("D:" + File.separator + "raoxianlong" + File.separator + "test.txt");
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        // 2.通过FileOutputStream创建OutputStream的子类
        OutputStream out = new FileOutputStream(file);

        // 3.写入数据
        byte[] test = "饶先龙是傻逼66666!".getBytes();

        // 3.1 通过单个字节写入
        /*for (byte b : test){
            out.write(b);
        }*/
        // 3.2 通过全部字节写入
        out.write(test);
        // 3.3 通过部分字节写入  off 开始写入字节的位置 len 写入的字节长度 汉字占两个字节
        //out.write(test, 6, 6);

        // 4.关闭流
        out.close();
    }





}
