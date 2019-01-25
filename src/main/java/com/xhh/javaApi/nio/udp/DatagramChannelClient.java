package com.xhh.javaApi.nio.udp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 *  使用UDP
 */
public class DatagramChannelClient {


    /**
     * 客户端
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.nextLine();
            buffer.put((new Date().toString() + "\n" + str).getBytes());
            buffer.flip();
            dc.send(buffer, new InetSocketAddress("127.0.0.1", 8890));
            buffer.clear();
        }
        dc.close();
    }


}
