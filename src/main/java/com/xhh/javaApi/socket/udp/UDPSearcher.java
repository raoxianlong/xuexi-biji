package com.xhh.javaApi.socket.udp;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UDPSearcher {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);

        new ReceiveThread(ds).start();

        while (scanner.hasNext()){
            byte[] data = scanner.nextLine().getBytes();
            DatagramPacket datagramPacket =  new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 2000);
            ds.send(datagramPacket);
        }
    }

    public static class ReceiveThread extends Thread{

        DatagramSocket ds;

        public ReceiveThread(DatagramSocket ds){
            this.ds =ds;
        }

        @Override
        public void run() {
          super.run();

          byte[] data = new byte[1024];
          DatagramPacket packet = new DatagramPacket(data, data.length);
          while (true){
              try {
                  ds.receive(packet);
                  String ip = packet.getAddress().getHostAddress();
                  int port = packet.getPort();
                  String reData = new String(packet.getData(), 0, packet.getLength());
                  System.out.println(ip + ":" + port + "\n"+ reData);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
    }

}
