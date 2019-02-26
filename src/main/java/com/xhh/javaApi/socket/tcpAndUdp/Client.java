package com.xhh.javaApi.socket.tcpAndUdp;

import com.xhh.javaApi.socket.tcpAndUdp.bean.ServerInfo;
import com.xhh.javaApi.socket.tcpAndUdp.tcp.TcpClient;

import java.io.IOException;

public class Client {


    public static void main(String[] args) {
       ServerInfo serverInfo =  ClientSearcher.searchServer(1000000);

       if(serverInfo != null){
           try {
               TcpClient.linkWith(serverInfo);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
}
