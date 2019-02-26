package com.xhh.javaApi.socket.tcpAndUdp.tcp;

import com.xhh.javaApi.socket.tcpAndUdp.tcp.handle.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *  TCP Server
 *    开启服务、关闭服务、监听请求、处理请求
 */
public class TcpServer {

    private final int port;
    private ClientListener clientListener;
    private List<ClientHandler> clientHandlerList = new ArrayList<>();

    public TcpServer(int port) {
        this.port = port;
    }

    /**
     *  开启服务
     *    创建监听请求线程循环接受请求
     * @return
     */
    public boolean start(){
        try {
            clientListener = new ClientListener(port);
            clientListener.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void stop(){
        if (clientListener != null){
            clientListener.exit();
        }
        for (ClientHandler clientHandler : clientHandlerList){
            clientHandler.exit();
        }
        clientHandlerList.clear();
    }

    public void sendBroadcast(String str){
        for (ClientHandler clientHandler : clientHandlerList){
            clientHandler.send(str);
        }
    }

    /**
     *  客户端监听线程
     */
    private class ClientListener extends Thread{
        private ServerSocket serverSocket;
        private boolean done = false;

        public ClientListener(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器信息："+ serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        }

        @Override
        public void run() {
            System.out.println("服务器准备就绪...");
            Socket socket;
            while (!done){
                try {
                    socket = serverSocket.accept();
                    // 将socket交给处理类处理
                    ClientHandler clientHandler = new ClientHandler(socket,
                            close -> clientHandlerList.remove(close));
                    clientHandler.readToPrint();
                    clientHandlerList.add(clientHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        void exit(){
            done = true;
            CloseUtils.close(serverSocket);
        }
    }

}
