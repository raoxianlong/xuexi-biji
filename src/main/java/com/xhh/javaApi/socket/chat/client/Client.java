package com.xhh.javaApi.socket.chat.client;


import com.xhh.javaApi.socket.chat.constant.UDPConstants;
import com.xhh.javaApi.socket.chat.util.ByteUtils;
import com.xhh.javaApi.socket.chat.util.CloseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  接受广播
 */
public class Client {

    private static Listener LISTENER;
    private final int clientPort;

    public Client(int clientPort) {
        this.clientPort = clientPort;
    }

    public void start(){
        stop();
        LISTENER = new Listener();
        LISTENER.start();
    }

    public void stop(){
        if (LISTENER != null){
            LISTENER.exit();
            LISTENER = null;
        }
    }


    private class Listener extends Thread{
        private boolean done = false;
        private DatagramSocket datagramSocket;
        private final byte[] buffer = new byte[128];
        private final ExecutorService executorService;

        public Listener() {
            this.executorService = Executors.newSingleThreadExecutor();
        }

        @Override
        public void run() {
            try {
                datagramSocket = new DatagramSocket(Client.this.clientPort);
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

                System.out.println("客户端准备就绪.");
                while (!done){
                    datagramSocket.receive(datagramPacket);

                    String address = datagramPacket.getAddress().getHostAddress();
                    int port = datagramPacket.getPort();
                    int dataLen = datagramPacket.getLength();

                    System.out.println("来自["+ address + ":" + port +"]的消息:" + new String(buffer, 0, dataLen));

                    boolean isValid = dataLen > UDPConstants.HEADER.length && ByteUtils.verifyHeader(buffer);
                    // 是邀请聊天室的消息
                    if (isValid){
                        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, UDPConstants.HEADER.length, dataLen);
                        short cmd = byteBuffer.getShort();
                        if (cmd != UDPConstants.CLIENT_CMD){
                            System.out.println("这是一条假的邀请消息!");
                            continue;
                        }
                        int serverPort = byteBuffer.getInt();
                        executorService.execute(new InviteHandler(serverPort));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                exit();
            }
        }

        public void exit(){
            System.out.println("客户端已关闭.");
            done = true;
            CloseUtils.close(datagramSocket);
        }

        private class InviteHandler implements Runnable{
            private final int port;

            public InviteHandler(int port) {
                this.port = port;
            }

            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    String str;
                    do {
                        System.out.println("您是否进入聊天室？[Y/N](聊天室信息："+ Inet4Address.getLocalHost() + ":" + port +")");
                        str= reader.readLine();
                    }while (!("N".equals(str) || "Y".equals(str)));

                    if ("Y".equals(str)){
                        do {
                            System.out.print("请您输入聊天室昵称：");
                            str= reader.readLine();
                        }while (str == null || str.trim().equals(""));

                        TCPClient tcpClient = new TCPClient(port, str);
                        tcpClient.start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
