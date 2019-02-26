package com.xhh.javaApi.socket.chat.util;

import com.xhh.javaApi.socket.chat.constant.UDPConstants;

public class ByteUtils {



    public static boolean verifyHeader(byte[] data){
        if (data.length < UDPConstants.HEADER.length){
            return false;
        }

        for (int i=0;i < UDPConstants.HEADER.length;i++ ){
            if (UDPConstants.HEADER[i] != data[i]){
                return false;
            }
        }
        return true;
    }


}
