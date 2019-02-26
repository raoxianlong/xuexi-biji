package com.xhh.javaApi.socket.chat.server;


import com.xhh.javaApi.socket.chat.constant.TCPConstants;
import com.xhh.javaApi.socket.tcpAndUdp.tcp.CloseUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  聊天室服务器
 */
public class Server implements ServerHandler.HandleCallback{

    private List<ServerHandler> handlerList = new ArrayList<>();
    private Listenter listenter;
    private Selector selector;
    private ServerSocketChannel server;

    /**
     *  开启选择器
     *  开启服务器渠道
     *  绑定端口
     *  将服务器渠道注册到选择器
     */
    public void start(){
        try {
            selector = Selector.open();
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(TCPConstants.SERVER_PORT));
            server.register(selector, SelectionKey.OP_ACCEPT);
            listenter = new Listenter();
            listenter.start();
            System.out.println("服务器准备就绪.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        handlerList.stream().forEach(handler -> {
            handler.stop();
        });
        handlerList.clear();
        listenter.exit();
    }

    @Override
    public void disconnect(ServerHandler handler) {
        handler.stop();
        handlerList.remove(handler);
    }

    @Override
    public void onReceiveMessage(String msg) {
        handlerList.stream().forEach(handler -> {
            handler.sendMsg(msg);
        });
    }

    private class Listenter extends Thread{
        private boolean done;

        @Override
        public void run() {
            do {
                try {
                    if (selector.select() == 0){
                        if (done){
                            break;
                        }
                        continue;
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        if (done){
                            break;
                        }
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()){
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();

                            ServerHandler serverHandler = new ServerHandler(socketChannel, Server.this);
                            serverHandler.startReadToPrint();

                            synchronized (Server.this){
                                handlerList.add(serverHandler);
                            }
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while (!done);

        }

        public void exit(){
            done = true;
        }
    }

}
