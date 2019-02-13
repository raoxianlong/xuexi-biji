package com.xhh.javaApi.socket.message;

public class MessageCreator {
    private static final String SN_HEADER = "收到暗号, 我是SN:";
    private static final String PORT_HEADER = "这是暗号, 请回电端口:";

    public static String buildWithPort(int prot){
        return PORT_HEADER + prot;
    }

    /**
     *  解析端口
     * @return
     */
    public static int parsePort(String data){
        if (data.startsWith(PORT_HEADER)){
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }
        return -1;
    }

    public static String buildWithSn(String sn){
        return SN_HEADER + sn;
    }

    public static String parseSn(String data){
        if (data.startsWith(SN_HEADER)){
            return data.substring(SN_HEADER.length());
        }
        return null;
    }


}
