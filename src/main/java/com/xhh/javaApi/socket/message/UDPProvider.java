package com.xhh.javaApi.socket.message;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * UDP 提供者，用于提供服务
 */
public class UDPProvider {

    public static void main(String[] args) throws IOException {
        String sn = UUID.randomUUID().toString().toLowerCase();
        Provider provider = new Provider(sn);
        provider.start();

        System.in.read();
        provider.exit();
    }

    private static class Provider extends Thread{
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }
        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvider Started.");

            try{
                //第一步： 监听20000端口
                ds = new DatagramSocket(20000);

                while (!done){
                    //第二步： 构建接受实体
                    final byte[] buf = new byte[512];
                    DatagramPacket rsPacket = new DatagramPacket(buf, buf.length);

                    //第三步：接受
                    ds.receive(rsPacket);

                    //第四步：打印接受到的信息与发送者的信息
                    String ip = rsPacket.getAddress().getHostAddress();
                    int port = rsPacket.getPort();
                    int dataLen = rsPacket.getLength();
                    String data = new String(rsPacket.getData(),0 ,dataLen);
                    System.out.println("UDPProvider receive form ip:" + ip
                            +"\tport:"+ port + "\tdata:" + data);

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1){
                        // 构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] bytes = responseData.getBytes();
                        DatagramPacket datagramPacket = new DatagramPacket(bytes,
                                bytes.length,
                                rsPacket.getAddress(),
                                responsePort);
                        ds.send(datagramPacket);
                    }
                }
            }catch (Exception ignored){
            }finally {
                close();
            }
        }

        private void close(){
            if (ds != null){
                ds.close();
                ds = null;
            }
        }

        void exit(){
            done = true;
            close();
        }

    }



}
