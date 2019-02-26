package com.xhh.javaApi.socket.tcpAndUdp;


import com.xhh.javaApi.socket.tcpAndUdp.bean.ServerInfo;
import com.xhh.javaApi.socket.tcpAndUdp.constant.UDPConstants;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***
 *  客户端搜索
 */
public class ClientSearcher {

    public static final int LISTEN_PORT = UDPConstants.PORT_CLIENT_RESPONSE;


    /**
     *  搜索客户端服务器
     *    第一步： 创建监听线程，等待监听线程启动
     *    第二步： 监听线程启动后，发送广播数据包
     *    第三步： 在监听线程中查看是否有回送的数据包，解析端口和IP
     * @return
     */
    public static ServerInfo searchServer(int timeout){
        System.out.println("开始搜索服务器.");
        CountDownLatch receiveLatch = new CountDownLatch(1);
        Listener listener = null;
        try {
            listener = listen(receiveLatch);
            sendBroadcast();
            receiveLatch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("搜索完成.");
        List<ServerInfo> serverInfoList = listener.getServersAndClose();
        for (ServerInfo info : serverInfoList){
            System.out.println(info);
        }
        if (!serverInfoList.isEmpty()){
            return serverInfoList.get(0);
        }
        return null;
    }

    /**
     *  监听
     * @param receiveLatch
     * @return
     * @throws InterruptedException
     */
    public static Listener listen(CountDownLatch receiveLatch) throws InterruptedException {
        System.out.println("UDPSearcher 监听启动.");
        CountDownLatch startDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, startDownLatch, receiveLatch);
        listener.start();
        startDownLatch.await();
        return listener;
    }

    /**
     *  发送广播
     *
     */
    public static void sendBroadcast() throws IOException {
        System.out.println("开始发送广播");
        DatagramSocket datagramSocket = new DatagramSocket();
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        // 头部
        byteBuffer.put(UDPConstants.HEADER);
        // CMD
        byteBuffer.putShort((short) 1);
        // 要监听的端口
        byteBuffer.putInt(LISTEN_PORT);

        DatagramPacket packet = new DatagramPacket(byteBuffer.array(), byteBuffer.position() + 1);
        String broadcastAddress =UDPConstants.getLocalBroadCast();
        packet.setAddress(InetAddress.getByName(broadcastAddress));
        packet.setPort(UDPConstants.PORT_SERVER);
        datagramSocket.send(packet);
        //datagramSocket.close();
        System.out.println("广播发送完成");
    }


    /**
     *  监听线程
     *      监听客户端Server发送来的数据包，封装到ServerInfo当中
     */
    private static class Listener extends Thread{
           private final int listenPort;
           private final CountDownLatch startDownLatch;
           private final CountDownLatch receiveDownLatch;
           private final List<ServerInfo> serverInfoList = new ArrayList<>();
           private final byte[] buffer = new byte[128];
           private final int minLen = UDPConstants.HEADER.length + 2 + 4;
           private boolean done = false;
           private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch startDownLatch, CountDownLatch receiveDownLatch) {
            this.listenPort = listenPort;
            this.startDownLatch = startDownLatch;
            this.receiveDownLatch = receiveDownLatch;
        }

        @Override
        public void run() {
            startDownLatch.countDown();
            try {
                // 监听回送端口
                ds = new DatagramSocket(listenPort);
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                while (!done){
                    System.out.println("done:" + done);
                    ds.receive(datagramPacket);
                    // 接收完成之后解析发送者的ip、port、sn
                    String ip = datagramPacket.getAddress().getHostAddress();
                    int port = datagramPacket.getPort();
                    int dataLen = datagramPacket.getLength();
                    byte[] data = datagramPacket.getData();
                    // 验证数据有效性
                    boolean isValid = dataLen >= minLen && verifyHeader(data);
                    if (!isValid){
                        continue;
                    }
                    System.out.println("搜索到服务器\t"
                            + "ip:" + ip +"\t"
                            + "port:" + port);

                    ByteBuffer byteBuffer =
                            ByteBuffer.wrap(buffer, UDPConstants.HEADER.length, dataLen);

                    final short cmd = byteBuffer.getShort();
                    final int serverPort = byteBuffer.getInt();
                    if (cmd != 2 || serverPort<=0){
                        System.out.println("无效的数据" +
                                "：{cmd：" + cmd + ", port "+ serverPort + "}");
                        continue;
                    }
                    String sn = new String(buffer, minLen, dataLen - minLen);
                    ServerInfo serverInfo = new ServerInfo(sn, ip, serverPort);
                    serverInfoList.add(serverInfo);
                    // 成功搜索到一个客户端服务器
                    receiveDownLatch.countDown();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                close();
            }
        }

        public void close(){
            if (ds != null){
                System.out.println("执行关闭操作");
                ds.close();
                ds = null;
            }
        }

        List<ServerInfo> getServersAndClose(){
            done = true;
            close();
            return serverInfoList;
        }

        /**
         * 验证数据头部的有效性
         * @return
         */
        private static boolean verifyHeader(byte[] data){
            if (data.length < UDPConstants.HEADER.length){
                return false;
            }

            for (int i=0;i < UDPConstants.HEADER.length;i++){
                if (UDPConstants.HEADER[i] != data[i]){
                    return false;
                }
            }
            return true;
        }
    }



}
