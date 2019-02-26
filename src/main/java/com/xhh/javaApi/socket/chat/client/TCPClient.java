package com.xhh.javaApi.socket.chat.client;

import com.xhh.javaApi.socket.tcpAndUdp.tcp.CloseUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    private final int port;
    private String nickName;
    private Socket socket;
    private ReadHandler readHandler;

    public TCPClient(int port, String nickName) {
        this.port = port;
        this.nickName = nickName;
    }

    public void start(){
        try {
            stop();
            socket = new Socket();
            socket.connect(new InetSocketAddress(Inet4Address.getByName("127.0.0.1"), port), 3000);
            // 读取数据
            readHandler =  new ReadHandler(socket.getInputStream());
            readHandler.start();
            // 写数据
            write(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            stop();
        }
    }

    public void stop(){
        if (readHandler != null){
            readHandler.exit();
            readHandler = null;
        }
        if (socket != null){
            CloseUtils.close(socket);
            socket = null;
        }
    }

    private void write(OutputStream outputStream){
        PrintStream print = new PrintStream(outputStream);
        print.println("欢迎【" + nickName + "】成功进入聊天室!");
        Scanner scanner = new Scanner(System.in);
        try {
            while (scanner.hasNext()){
                String str = scanner.nextLine();
                if ("bye".equals(str)){
                    print.println("【" + nickName + "】退出聊天室!");
                    break;
                }
                print.println(nickName + ":" + str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  读取数据
     */
    private static class ReadHandler extends Thread{
        private final InputStream inputStream;
        private boolean done = false;

        public ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while (!done){
                        String str = reader.readLine();
                        if (str == null) {
                            break;
                        }
                        System.out.println(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                exit();
            }
        }

        public void exit(){
            done = true;
            CloseUtils.close(inputStream);
        }
    }

}
