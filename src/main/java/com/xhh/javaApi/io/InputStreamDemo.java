package com.xhh.javaApi.io;

import org.junit.Test;

import java.io.*;

public class InputStreamDemo {

     static File file = new File("D:" + File.separator + "raoxianlong" + File.separator + "test.txt");

    /**
     *  单个字节读取
     * @throws FileNotFoundException
     */
    @Test
    public void readOne() throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[1024];
        int temp;
        int flag = 0;
        while ((temp=in.read()) != -1){
            data[flag++] = (byte) temp;
        }
        in.close();
        System.out.println(new String(data, 0, flag));
    }

    /**
     *  全部字节读取:
     *          要注意的是读取的长度，跟缓存数组的长度有关，
     *          数组长度大于输入流中字节的长度则可以全部读完。
     *          相当于：read(data, 0, data.length);
     */
    @Test
    public void readAll() throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[1024];
        int len = in.read(data);
        in.close();
        System.out.println(new String(data, 0, len));
    }

    /**
     *  读取部分字节
     */
    @Test
    public void readMany() throws IOException {
        InputStream in = new FileInputStream(file);

        byte[] temp = new byte[2];
        byte[] data = new byte[1024];

        int off = 0; // 角标
        int foot = 0; // 角标
        int len; // 定义每次读取两个字节

        //每次读取两个
        while ((len = in.read(temp, off, temp.length)) != -1){
            for (int i=0; i < len;i++){
                data[foot++] = temp[i];
            }
        }
        in.close();
        System.out.println(new String(data, 0, foot));
    }

    /**
     *  文件拷贝
     */
    @Test
    public void copy(){

    }


}
