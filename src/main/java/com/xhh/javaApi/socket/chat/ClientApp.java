package com.xhh.javaApi.socket.chat;

import com.xhh.javaApi.socket.chat.client.Client;
import com.xhh.javaApi.socket.chat.client.SearchClient;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        Client client = new Client(20005);
        client.start();
        Thread.sleep(1000);
        SearchClient.sendBroadcast(20005);
    }


}
