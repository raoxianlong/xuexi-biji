package com.xhh.javaApi.bio;

import org.junit.Test;

import java.io.*;

public class ReaderDemo {

    static File file = new File("D:" + File.separator + "raoxianlong" + File.separator  + "testWriter.txt");

    /**
     * 单个字符读取
     */
    @Test
    public void readOne() throws IOException {
        Reader reader = new FileReader(file);

        StringBuilder stringBuilder = new StringBuilder();

        int temp;

        while ((temp = reader.read()) != -1){
            stringBuilder.append((char) temp);
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * 读取多个字符
     */
    @Test
    public void readMany() throws IOException{
        Reader reader = new FileReader(file);

        StringBuilder stringBuilder = new StringBuilder();

        // 每次读取4个字符
        char[] temp = new char[4];
        int len;

        while ((len = reader.read(temp)) != -1){
            stringBuilder.append(temp, 0, len);
        }
        System.out.println(stringBuilder.toString());
    }

}
