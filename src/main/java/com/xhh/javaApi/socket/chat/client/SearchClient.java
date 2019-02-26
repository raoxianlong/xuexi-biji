package com.xhh.javaApi.socket.chat.client;


import com.xhh.javaApi.socket.chat.constant.TCPConstants;
import com.xhh.javaApi.socket.chat.constant.UDPConstants;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

/**
 *  搜索客户端
 */
public class SearchClient {

    /**
     *  发送一份广播
     */
    public static void sendBroadcast(int port) throws IOException, InterruptedException {
        DatagramSocket datagramSocket = new DatagramSocket();

        ByteBuffer buffer = ByteBuffer.allocate(128);
        buffer.put(UDPConstants.HEADER);
        buffer.putShort(UDPConstants.CLIENT_CMD);
        buffer.putInt(TCPConstants.SERVER_PORT);

        DatagramPacket datagramPacket = new DatagramPacket(buffer.array(), buffer.position() + 1,
                Inet4Address.getByName("192.168.79.255"), port);

        datagramSocket.send(datagramPacket);
        Thread.sleep(2000);
        datagramSocket.close();
    }



}
