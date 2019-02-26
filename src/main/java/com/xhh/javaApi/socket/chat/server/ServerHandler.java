package com.xhh.javaApi.socket.chat.server;

import com.xhh.javaApi.socket.tcpAndUdp.tcp.CloseUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {
    private final SocketChannel socket;
    private final ReaderHandler readerHandler;
    private final WriteHandler writeHandler;
    private final HandleCallback callback;


    public ServerHandler(SocketChannel socket, HandleCallback callback) throws IOException {
        this.socket = socket;
        this.callback = callback;
        socket.configureBlocking(false);
        Selector selector = Selector.open();
        socket.register(selector, SelectionKey.OP_READ);
        this.readerHandler = new ReaderHandler(selector);
        this.writeHandler = new WriteHandler(selector);
        System.out.println("客户端：" + socket.getRemoteAddress() + "已连接.");
    }

    public void startReadToPrint(){
        this.readerHandler.start();
    }

    public void sendMsg(String msg){
        writeHandler.send(msg);
    }

    public void stop() throws IOException {
        System.out.println("客户端：" + socket.getRemoteAddress() + "退出连接.");
        readerHandler.exit();
        writeHandler.exit();
        CloseUtils.close(socket);
    }

    public interface HandleCallback{
        // 断开连接
        void disconnect(ServerHandler handler);
        // 收到消息
        void onReceiveMessage(String msg);
    }

    private class WriteHandler{
        private final ExecutorService executorService;
        private final Selector selector;

        public WriteHandler(Selector selector) {
            this.selector = selector;
            this.executorService = Executors.newSingleThreadExecutor();
        }

        public void send(String msg){
            this.executorService.execute(new WriteRunnable(msg));
        }

        public void exit(){
            CloseUtils.close(selector);
            executorService.shutdownNow();
        }

        private class WriteRunnable implements Runnable{
            private String msg;

            public WriteRunnable(String msg) {
                this.msg = msg;
            }

            @Override
            public void run() {

            }
        }
    }

    private class ReaderHandler extends Thread{
        private boolean done = false;
        private final Selector selector;
        private ByteBuffer byteBuffer;

        public ReaderHandler(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try{
                do {
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

                        if (key.isReadable()){
                            SocketChannel channel = (SocketChannel) key.channel();
                            byteBuffer.clear();
                            int read = channel.read(byteBuffer);
                            if (read > 0){
                                String str = new String(byteBuffer.array(), 0, read - 1);
                                callback.onReceiveMessage(str);
                            }
                        }
                    }
                }while (done);
            }catch (Exception e){
                if (!done){
                    System.out.println("");
                }
            }
        }

        void exit(){
            done = true;
            CloseUtils.close(selector);
        }
    }





}
