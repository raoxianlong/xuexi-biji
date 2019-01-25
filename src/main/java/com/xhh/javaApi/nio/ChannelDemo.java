package com.xhh.javaApi.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *  通道练习
 */
public class ChannelDemo {


    static File file = new File("D:" + File.separator + "raoxianlong");


    /**
     *  分散读取和聚集写入
     */
    @Test
    public void disperse() throws IOException {
        // 1. 获取渠道(三种方式)
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(file, "222.txt"), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
         // 2. 按顺序分散读取数据到缓冲区
        ByteBuffer buff1 = ByteBuffer.allocate(500);
        ByteBuffer buff2 = ByteBuffer.allocate(500);
        ByteBuffer buff3 = ByteBuffer.allocate(500);
        fileChannel.read(new ByteBuffer[]{buff1, buff2, buff3});
        // 3. 从缓冲区获取数据
        buff1.flip();
        buff2.flip();
        buff3.flip();
        System.out.println(new String(buff1.array(), 0, buff1.limit()));
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println(new String(buff2.array(), 0, buff2.limit()));
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println(new String(buff3.array(), 0, buff3.limit()));
        fileChannel.close();
        // 4. 聚集写入
        FileChannel outChannel = FileChannel.open(Paths.get("D:","raoxianlong", "333.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        outChannel.write(new ByteBuffer[]{buff1, buff2, buff3});
        outChannel.close();
    }

    /**
     * 通道之间的数据传输(直接内存缓冲区)
     */
    @Test
    public void transfer() throws IOException {
        FileChannel in =  FileChannel.open(Paths.get("D:","raoxianlong", "b.jpg"), StandardOpenOption.READ);
        FileChannel out =  FileChannel.open(Paths.get("D:","raoxianlong", "e.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        in.transferTo(0, in.size(), out);
        in.close();
        out.close();
    }

    /**
     *  使用通道进行文件复制
     */
    @Test
    public void copy(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inputStream = new FileInputStream(new File(file, "b.jpg"));
            File file1 = new File(file,"c.jpg");
            outputStream = new FileOutputStream(file1);

            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1){
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  通过直接内存进行Copy操作
     */
    @Test
    public void directCopy(){
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inChannel = FileChannel.open(Paths.get("D:","raoxianlong",  "b.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("D:","raoxianlong",  "d.jpg"),StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            MappedByteBuffer inBuffer =  inChannel.map(FileChannel.MapMode.READ_ONLY,0, inChannel.size());
            MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE,0, inChannel.size());

            byte[] temp = new byte[inBuffer.limit()];
            inBuffer.get(temp);
            outBuffer.put(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  字符编码和解码
     */
    @Test
    public void charset() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");

        String str = "水电费聚隆科技的双方各";

        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put(str);
        charBuffer.flip();

        ByteBuffer buffer = encoder.encode(charBuffer);
        buffer.flip();

        while (buffer.remaining() > 0){
            System.out.println(buffer.get());
        }
    }



}
