package com.xhh.javaApi.socket.message;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * UDP 搜索者， 用于搜索服务支持方
 */
public class UDPSearcher {

    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("UDPSearcher started.");

        Listener listener = listen();
        sendBroadcast();

        System.in.read();

        for (Device device : listener.getDevicesAndClose()){
            System.out.println("Device:" + device.toString());
        }

    }


    private static class Device{
        final int port;
        final String ip;
        final String sn;

        public Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }

    private static Listener listen() throws InterruptedException {
        System.out.println("UDPSearcher start listen.");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();

        countDownLatch.await();
        return listener;
    }



    private static void sendBroadcast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast started.");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建一份请求数据
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] bytesData = requestData.getBytes();

        // 直接构建packet
        DatagramPacket dp = new DatagramPacket(bytesData, bytesData.length);
        System.out.println("广播地址:" + getLocalBroadCast());
        dp.setAddress(InetAddress.getByName(getLocalBroadCast()));
        dp.setPort(20000);

        // 发送
        ds.send(dp);
        ds.close();

        System.out.println("UDPSearcher sendBroadcast finished.");
    }

    public static String getLocalBroadCast(){
        String broadCastIp = null;
        try {
            Enumeration<?> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) netInterfaces.nextElement();
                if (!netInterface.isLoopback()&& netInterface.isUp()) {
                    List<InterfaceAddress> interfaceAddresses = netInterface.getInterfaceAddresses();
                    for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                        //只有 IPv4 网络具有广播地址，因此对于 IPv6 网络将返回 null。
                        if(interfaceAddress.getBroadcast()!= null){
                            broadCastIp =interfaceAddress.getBroadcast().getHostAddress();

                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return broadCastIp;
    }


    private static class Listener extends Thread{

        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            // 通知已启动
            countDownLatch.countDown();
            try{

                ds = new DatagramSocket(listenPort);

                while (!done){
                    // 构建接受实体
                    final byte[] buf = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                    // 接收
                    ds.receive(datagramPacket);

                    // 发送者IP
                    String ip = datagramPacket.getAddress().getHostAddress();
                    int port = datagramPacket.getPort();
                    int dataLen = datagramPacket.getLength();
                    String data = new String(datagramPacket.getData(),0 , dataLen);
                    System.out.println("UDPSearcher receive form ip:" + ip
                     + "\tport:" + port +"\tdata:" + data );

                    String sn = MessageCreator.parseSn(data);
                    if (sn != null){
                        Device device = new Device(port, ip, sn);
                        devices.add(device);
                    }
                }
            }catch (Exception e){

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

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }


    }


}
