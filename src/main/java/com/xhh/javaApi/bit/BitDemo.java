package com.xhh.javaApi.bit;

public class BitDemo {


    public static void main(String[] args) {
         //binaryToDecimal(-0);
       /* byte[] bytes = intToByte(10);
         for (byte b : bytes){
             System.out.print(b+",");
         }
        System.out.println(bytesToInt(bytes))*/;
//        System.out.println(2&3);
        System.out.println(abs(-99999999));
        System.out.println(abs(-8888454));
        System.out.println(abs(-99996669));
        System.out.println(abs(-1));
        System.out.println(abs(-0));
        System.out.println(abs(-0));
        Math.abs(-2);
    }

    /**
     *  int型转二进制
     * @param a
     */
    public static void binaryToDecimal(int a){
        for (int i = 31;i>0;i--){
            System.out.print(a >>> i & 1);
        }
    }

    /**
     * int型转字节数组数组
     */
    public static byte[] intToByte(int a){
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((a >> 24) & 0xff);
        bytes[1] = (byte) ((a >> 16) & 0xff);
        bytes[2] = (byte) ((a >> 8) & 0xff);
        bytes[3] = (byte) (a & 0xff);
        return bytes;
    }

    /**
     * 字节转int
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes){
        return  ((bytes[0] & 0xff) << 24) |
                ((bytes[1] & 0xff) << 16) |
                ((bytes[2] & 0xff) << 8) |
                (bytes[3] & 0xff);
    }

    /**
     * short转字节数组
     * @return
     */
    public byte[] shortToBytes(short s){
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((s >> 8) & 0xff);
        bytes[1] = (byte) (s & 0xff);
        return bytes;
    }


    public static int abs(int a){
        /*if (((a >> 31) & 1) == 1){
            return ~(a - 1);
        }
        return a;*/
        return a < 0 ? -a : a;

    }


}
