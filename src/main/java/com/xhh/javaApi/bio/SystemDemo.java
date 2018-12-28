package com.xhh.javaApi.bio;

import org.junit.Test;

import java.io.*;

/**
 *  系统输入、输出
 */
public class SystemDemo {

    static File file = new File("D:" + File.separator + "raoxianlong" + File.separator + "out.txt");

    /**
     *  系统输出
     * @throws IOException
     */
    @Test
    public void systemOut() throws IOException {
        OutputStream out = System.out;
        out.write("饶先龙是SB".getBytes());

        // 改变系统输出
        System.setOut(new PrintStream(new FileOutputStream(file)));
        System.out.println("666666666666666666666666666666");
    }


    @Test
    public void systemIn() throws IOException {
        InputStream in = System.in;
        byte[] data = new byte[1024];
        System.out.println("请输入数据：");
        int len = in.read(data);
        System.out.println(new String(data, 0, len));
    }






}
