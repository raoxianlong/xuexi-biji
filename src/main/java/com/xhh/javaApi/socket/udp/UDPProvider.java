package com.xhh.javaApi.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UDPProvider {

    public static void main(String[] args) throws IOException {
        // 创建接受者
        DatagramSocket ds = new DatagramSocket(2000);
        // 创建一个接受数据的缓冲数组
        byte[] buffer = new byte[1024];

        // 创建数据接受包
        DatagramPacket rPacket = new DatagramPacket(buffer, buffer.length);

        Set<String> ips = new HashSet<>();

        while (true){
            // 接受数据
            ds.receive(rPacket);
            //打印收到的信息
            String ip = rPacket.getAddress().getHostAddress();
            int port = rPacket.getPort();
            int dataLen = rPacket.getLength();
            String data = new String(rPacket.getData(), 0, dataLen);
            String address = ip + ":" + port;
            System.out.println(address + "\n"+ data);
            if (!ips.contains(address)){
                new RevertThread(ds, rPacket.getAddress(), port).start();
            }else{
                ips.add(address);
            }
        }
    }

    public static class RevertThread extends Thread{

        DatagramSocket ds;
        InetAddress revertAddress;
        int revertPort;

        public RevertThread(DatagramSocket ds, InetAddress revertAddress, int revertPort){
            this.ds =ds;
            this.revertAddress = revertAddress;
            this.revertPort = revertPort;
        }

        @Override
        public void run() {
            super.run();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                try {
                    byte[] data = scanner.nextLine().getBytes();
                    DatagramPacket rPacket = new DatagramPacket(data, data.length, revertAddress, revertPort);
                    ds.send(rPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
