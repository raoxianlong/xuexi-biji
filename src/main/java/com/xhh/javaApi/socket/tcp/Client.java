package com.xhh.javaApi.socket.tcp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

/**
 * TCP 客户端
 *
 */
public class Client {


    private static void initSocket() throws UnknownHostException, SocketException {
         // 代理模式
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(Inet4Address.getByName("www.baidu.com"), 8890));
        Socket socket = new Socket(proxy);
        socket.setSoTimeout(3000);
        socket.setReuseAddress(true);
        socket.setTcpNoDelay(false);
        // 心跳包的作用
        socket.setKeepAlive(true);
        // 设置性能参数权重：短连接时间、延迟、带宽
        socket.setPerformancePreferences(1, 1, 1);
    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), Server.SERVER_PORT));
        System.out.println("已连接到服务器.");
        BufferedReader reader = null;
        PrintStream print = null;
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
            print = new PrintStream(socket.getOutputStream());
            while (!done){
                if (scanner.hasNext()){
                    String msg = scanner.nextLine();
                    print.print(msg + "\n");
                    if ("bye".equals(msg)){
                        done = true;
                    }else{
                        String ack = reader.readLine();
                        System.out.println(ack);
                    }
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
