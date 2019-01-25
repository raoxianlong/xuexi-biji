package com.xhh.javaApi.socket;

import com.xhh.javaApi.utils.StringUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(8890));

        while (true){
            Socket socket = server.accept();
            System.out.println("客户端：" + socket.getInetAddress()+":" + socket.getPort() + "已连接");
            new Thread(new ServerThread(socket)).start();
        }
    }
}

class ServerThread implements Runnable{

    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader reader = null;
        PrintStream print = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            print = new PrintStream(socket.getOutputStream());

            while (true){
                String clientStr = reader.readLine();
                if ("bye".equals(clientStr)){
                    print.print("客户小鑫 : 您慢走");
                    break;
                }else{
                    System.out.println(clientStr);
                    clientStr = clientStr.substring(clientStr.indexOf(":") + 1);
                    clientStr = clientStr.replace("吗", "")
                                .replace("谁", "客户小鑫")
                                .replace("你", "我")
                                .replace("?", "!")
                                .replace("？", "!");
                    System.out.println("客户小鑫：" + clientStr);
                    print.println("客户小鑫：" + clientStr);
                }
            }
        } catch (IOException e) {
            System.out.println("客户端：" + socket.getInetAddress()+":" + socket.getPort() + "异常退出");
            e.printStackTrace();
        }finally {
            System.out.println("客户端：" + socket.getInetAddress()+":" + socket.getPort() + "退出连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}