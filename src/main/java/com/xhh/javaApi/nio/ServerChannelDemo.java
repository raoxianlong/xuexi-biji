package com.xhh.javaApi.nio;

import com.xhh.javaApi.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServerChannelDemo {

    /**
     *  服务端
     * @param args
     */
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        // 切换成非阻塞模式
        server.configureBlocking(false);
        // 绑定端口号
        server.bind(new InetSocketAddress(8089));
        Selector selector = Selector.open();
        // 将通道注册到选择器中
        server.register(selector, SelectionKey.OP_ACCEPT);
        // 轮询获取选择器上准备好的事件
        while (selector.select() > 0){
            // 获取所有准备好的选择键
            Iterator<SelectionKey>  iter = selector.selectedKeys().iterator();

            while (iter.hasNext()){
                SelectionKey key = iter.next();
                // 具体判断是什么事件准备就绪
                if (key.isAcceptable()){
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    // 获取当前选择器上准备就绪的通道
                    SocketChannel read = (SocketChannel)key.channel();;
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = read.read(buffer)) != -1){
                        buffer.flip();
                        String str = new String(buffer.array(), 0, len);
                        if (StringUtil.isNotBlank(str)){
                            System.out.println(str);
                        }
                        buffer.clear();
                    }
                }
                iter.remove();
            }
        }
    }
}
