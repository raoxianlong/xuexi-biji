package com.xhh.javaApi.socket.tcpAndUdp;

import com.xhh.javaApi.socket.tcpAndUdp.constant.TCPConstants;
import com.xhh.javaApi.socket.tcpAndUdp.tcp.TcpServer;

import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        TcpServer server = new TcpServer(TCPConstants.PORT_SERVER);
        server.start();

        ServerProvider.start(TCPConstants.PORT_SERVER);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.nextLine();
            if ("bye".equals(str)){
                break;
            }
            server.sendBroadcast(str);
        }
        ServerProvider.stop();
        server.stop();

    }
}
