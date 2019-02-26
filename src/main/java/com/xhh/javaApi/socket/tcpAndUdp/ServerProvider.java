package com.xhh.javaApi.socket.tcpAndUdp;


import com.xhh.javaApi.socket.tcpAndUdp.constant.UDPConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 *  客户端服务器，被搜索者
 *      服务启动
 *      服务关闭
 *
 */
public class ServerProvider {

    private static Provider PROVIDER_INSTANCE;


    static void start(int port){
        stop();
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn.getBytes() ,port);
        provider.start();
        System.out.println("服务端已启动.");
        PROVIDER_INSTANCE = provider;
    }

    static void stop(){
        if (PROVIDER_INSTANCE != null){
            PROVIDER_INSTANCE.exit();
            PROVIDER_INSTANCE = null;
        }
    }

    /**
     *  收到数据包之后,验证数据,将
     */
    private static class Provider extends Thread{
        private final byte[] sn;
        private final int port;
        private boolean done = false;
        private DatagramSocket ds = null;
        final byte[] buffer = new byte[128];

        public Provider(byte[] sn, int port) {
            this.sn = sn;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ds = new DatagramSocket(UDPConstants.PORT_SERVER);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                while (!done){
                    ds.receive(packet);

                    // 打印发送者的IP
                    String ip = packet.getAddress().getHostAddress();
                    int rPort = packet.getPort();
                    int dataLen = packet.getLength();
                    byte[] data = packet.getData();

                    boolean isValid = dataLen >= (UDPConstants.HEADER.length + 2 + 4);

                    System.out.println("接受到消息：" + "ip = " + ip + ":" + rPort);
                    if (!isValid){
                        continue;
                    }

                    // 解析命令与回端口
                    int index = UDPConstants.HEADER.length;
                    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, index, dataLen);
                    short cmd = byteBuffer.getShort();
                    int responsePort = byteBuffer.getInt();

                    if (cmd == 1 && responsePort > 0){
                        ByteBuffer responseBuffer = ByteBuffer.allocate(128);
                        responseBuffer.put(UDPConstants.HEADER);
                        responseBuffer.putShort((short)2);
                        responseBuffer.putInt(port);
                        responseBuffer.put(sn);
                        int len = responseBuffer.position();
                        DatagramPacket responsePacket;
                        responsePacket = new DatagramPacket(responseBuffer.array()
                                , len ,packet.getAddress(), responsePort);
                        ds.send(responsePacket);
                        System.out.println("回送完毕!");
                    }else{
                        System.out.println("无效的数据!");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                close();
            }
        }


        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        /**
         * 提供结束
         */
        void exit() {
            done = true;
            close();
        }

    }


}
