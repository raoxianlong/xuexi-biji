package com.xhh.javaApi.socket.tcpAndUdp;

import com.xhh.javaApi.socket.tcpAndUdp.constant.UDPConstants;

public class Server {

    public static void main(String[] args) {
        ServerProvider.start(UDPConstants.PORT_SERVER);
    }
}
