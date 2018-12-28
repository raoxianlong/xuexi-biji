package com.xhh.javaApi.bio;

import org.junit.Test;

import java.io.*;

public class ByteArrayInputStreamDemo {


    /**
     *  内存流测试
     */
   @Test
   public void test() throws IOException {
       byte[] bytes = "Hello World -Rxl wfwejlkjlkjs wefjklwwjljl!".getBytes();
       InputStream in = new ByteArrayInputStream(bytes);
       OutputStream out = new ByteArrayOutputStream();
        int temp;
        while ((temp = in.read()) != -1){
            out.write((char)Character.toUpperCase(temp));
        }
        String str = ((ByteArrayOutputStream) out).toString();
       in.close();
       out.close();
       System.out.println(str);

   }




}
