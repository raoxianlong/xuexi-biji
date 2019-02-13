package com.xhh.javaApi.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  TCP 服务器端
 *      需求： 接受来自客户端的请求数据并回复确认, 客户端发送bye是断开与客户端的连接
 */
public class Server {

    public static final int SERVER_PORT = 20000;

    /**
     *  步骤：
     *      1. 创建ServerSocket绑定端口号
     *      2. 接收来自客户端的请求
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        initServerSocket(server);
        System.out.println("server started.");
        while (true){
            Socket socket = server.accept();
            System.out.println("IP:" + socket.getInetAddress()+ ":" + socket.getPort() + "连接到服务器.");
            ServerHandle handle = new ServerHandle(socket);
            handle.start();
        }

    }
    /**
     *  初始化ServerSocket
     */
    private static void initServerSocket(ServerSocket server){

    }

    private static class ServerHandle extends Thread{

        private final Socket socket;
        private boolean done = false;

        private ServerHandle(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            super.run();
            BufferedReader reader = null;
            PrintStream print = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                print = new PrintStream(socket.getOutputStream());
                while (!done){
                    String msg = reader.readLine();
                    if ("bye".equals(msg)){
                        System.out.println("IP:" + socket.getInetAddress()+ ":" + socket.getPort() + "断开连接.");
                        done = true;
                    }else{
                        System.out.println("收到消息：" + msg);
                        print.println("已查看");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
