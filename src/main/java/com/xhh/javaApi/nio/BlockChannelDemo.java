package com.xhh.javaApi.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 */
public class BlockChannelDemo {

    /**
     *  使用阻塞模式传递数据
     *  客户端
     */
    @Test
    public void client() throws IOException {
        // 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8089));
        // 获取要传递的文件
        FileChannel inChannel = FileChannel.open(Paths.get("D:/raoxianlong/b.jpg"),StandardOpenOption.WRITE, StandardOpenOption.READ);
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) !=  -1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.shutdownOutput();
        inChannel.close();

        int len;
        while ((len = socketChannel.read(buffer)) != -1){
            System.out.println(new String(buffer.array(), 0, len));
        }
        socketChannel.close();
    }

    /**
     * 接受数据
     * 服务端
     */
    @Test
    public void server() throws IOException {
        // 创建通道
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8089));
        // 获取连接
        SocketChannel channel = server.accept();

        FileChannel outChannel = FileChannel.open(Paths.get("D:/raoxianlong/f2.jpg"),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (channel.read(buffer) != -1){
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        buffer.put("数据接受完毕".getBytes());
        buffer.flip();
        channel.write(buffer);
        channel.shutdownOutput();
    }




















}
