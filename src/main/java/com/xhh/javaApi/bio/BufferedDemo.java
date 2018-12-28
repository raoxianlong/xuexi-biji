package com.xhh.javaApi.bio;

import com.xhh.javaApi.utils.StringUtil;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;

/**
 *  缓冲区 Demo
 */
public class BufferedDemo {

    static File file = new File("D:" + File.separator + "raoxianlong" + File.separator  + "222.txt");


    /**
     *  通过缓冲区实现文件拷贝
     */
    @Test
    public void copy(){
        long start = System.currentTimeMillis();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            File outFile = new File(file.getParentFile(), "copyBuffered.txt");
            out = new BufferedOutputStream(new FileOutputStream(outFile));
            byte[] data = new byte[1024];
            int len;
            while ((len = in.read(data)) != -1){
                out.write(data, 0, len);
            }
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "ms");
    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;

        while (!exit){
            System.out.print("请输入姓名:");
            String name = reader.readLine();
            if (StringUtil.isNotBlank(name)){
                System.out.println("您的名字是：" + name);
                exit = true;
            }
        }
    }



}
