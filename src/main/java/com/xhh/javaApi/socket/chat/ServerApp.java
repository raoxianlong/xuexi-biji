package com.xhh.javaApi.socket.chat;

import com.xhh.javaApi.socket.chat.server.Server;

public class ServerApp {


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }


}
