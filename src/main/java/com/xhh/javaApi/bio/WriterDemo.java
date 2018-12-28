package com.xhh.javaApi.bio;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriterDemo {

    static File file = new File("D:" + File.separator + "raoxianlong" + File.separator  + "testWriter.txt");


    /**
     *  写入一个字符
     */
    @Test
    public void writerChar() throws IOException {
        Writer writer = new FileWriter(file, true);

        char[] chars = "饶先龙是傻逼".toCharArray();

        for (char c : chars){
            writer.write(c);
        }
        // 关闭之前刷新
        writer.close();
    }

    /**
     *  写入字符串
     */
    @Test
    public void writerStr() throws IOException {
        Writer writer = new FileWriter(file, true);

        String str = "饶先龙是大傻逼";

        writer.write(str);
        // 关闭之前刷新
        writer.close();
    }



    /**
     *  追加字符串
     *      必须在构造时设置是否追加
     */
    @Test
    public void appendStr() throws IOException {
        Writer writer = new FileWriter(file, true);

        String str = "饶先龙是大傻逼";

        writer.append(str);
        // 关闭之前刷新
        writer.close();
    }






}
