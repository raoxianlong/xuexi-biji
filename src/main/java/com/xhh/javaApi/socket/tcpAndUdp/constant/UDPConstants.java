package com.xhh.javaApi.socket.tcpAndUdp.constant;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

public class UDPConstants {

    // 公用头部
    public static byte[] HEADER = new byte[]{7, 7, 7, 7, 7, 7, 7, 7};
    // 服务器固化UDP接收端口
    public static int PORT_SERVER = 30201;
    // 客户端回送端口
    public static int PORT_CLIENT_RESPONSE = 30202;


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

}
