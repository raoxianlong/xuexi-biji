package com.xhh.javaApi.nio;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SocketChannelDemo {

    /**
     *  客户端 (阻塞模式)
     *
     *  传递一张图片
     */
    public static void main(String[] args) throws IOException {

        // 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8089));

        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        // 写入数据
        while (scanner.hasNext()){
            String str = "天子一号  "+ new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n" + scanner.nextLine();
            buffer.put(str.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
    }

}
