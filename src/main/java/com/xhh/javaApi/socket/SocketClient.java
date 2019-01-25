package com.xhh.javaApi.socket;

import com.xhh.javaApi.utils.StringUtil;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 *
 */
public class SocketClient {


    public static void main(String[] args) throws IOException {
        // 创建Socket 连接服务器地址和端口
        Socket socket = new Socket("127.0.0.1", 8890);

        System.out.println("已连接到服务器:" + socket.getInetAddress() + ":" + socket.getPort());
        OutputStream out = socket.getOutputStream();
        PrintStream print = new PrintStream(out);

        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner scanner = new Scanner(System.in);

        while (true){
            String str = scanner.nextLine();
            System.out.println("张三:" + str);
            print.println("张三:" + str);
            String inStr = reader.readLine();
            if ("bey".equals(inStr)){
                System.out.println("退出连接");
                break;
            }
            System.out.println(inStr);
        }
        socket.close();
    }
}


