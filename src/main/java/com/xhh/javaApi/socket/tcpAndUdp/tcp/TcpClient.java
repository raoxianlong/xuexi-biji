package com.xhh.javaApi.socket.tcpAndUdp.tcp;

import com.xhh.javaApi.socket.tcpAndUdp.bean.ServerInfo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {

    /**
     *  建立TCP连接
     *
     * @param serverInfo
     */
    public static void linkWith(ServerInfo serverInfo) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(Inet4Address.getByName(serverInfo.getIp()), serverInfo.getPort()), 3000);

        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息：" + socket.getLocalAddress() + " P:" + socket.getLocalPort());
        System.out.println("服务器信息：" + socket.getInetAddress() + " P:" + socket.getPort());

        ReadHandler readHandler = new ReadHandler(socket.getInputStream());
        readHandler.start();

        write(socket);
    }

    static void write(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = new PrintStream(socket.getOutputStream());

        while (scanner.hasNext()){
            String str = scanner.nextLine();
            if ("bye".equals(str)){
                break;
            }
            printStream.println(str);
        }
        printStream.close();
    }

    static class ReadHandler extends Thread{
        private boolean done;
        private final InputStream inputStream;

        public ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while (!done){
                   String str =  reader.readLine();
                   if (str == null){
                       System.out.println("连接已关闭!");
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

        void exit(){
            done = true;
            CloseUtils.close(inputStream);
        }
    }



}
