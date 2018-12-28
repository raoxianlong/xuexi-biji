package com.xhh.javaApi.bio;


import org.junit.Test;

import java.io.*;

/**
 *  转换流
 */
public class OutputStreamReaderTest {

    static File file = new File("D:" + File.separator + "raoxianlong" + File.separator  + "testWriter.txt");

    /**
     *  字节输入流转换为字符输入流
     */
    @Test
    public void input2Reader() throws IOException {
        InputStream inputStream = new FileInputStream(file);
        char[] chars = new char[1024 * 1024];
        Reader reader = new InputStreamReader(inputStream);
        int len = reader.read(chars);
        reader.close();
        System.out.println(new String(chars, 0 ,len));
    }

    /**
     * 字节输出流转换为字符输出流
     */
    @Test
    public void output2Writer() throws IOException {
        OutputStream out = new FileOutputStream(file);
        String str = "字节流、字符流、转换流";
        Writer writer = new OutputStreamWriter(out);
        writer.append(str);
        writer.close();
    }


    /**
     *  综合实例
     */
    @Test
    public void readerAndWriter() throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        char[] temp = new char[1024];
        int len;
        Writer writer = null;
        //
        //writer = new PrintWriter(System.out);  打印流

        //writer = new StringWriter();  字符串输出流

        while ((len = reader.read(temp)) != -1){
            writer.write(temp, 0, len);
        }
        writer.close();

        System.out.println(((StringWriter) writer).getBuffer());
    }


}
