package com.xhh.javaApi;

import java.time.Instant;

/**
 * 这个类演示java.lang.Number类 操作
 * @author  饶先龙
 * @version 1.0
 */
public class NumberDemo {


    /**
     * 这个方法演示java.lang.Number类的方法
     * 无返回值
     */
    public void methodDemo(){


    }

    public static void main(String[] args) {
        /*Double num =  new Double("250.250");

        System.out.println("byteValue() : "+ num.byteValue());
        System.out.println("shortValue() :" + num.shortValue());
        System.out.println("doubleValue() :" + num.doubleValue());
        System.out.println("floatValue() :" + num.floatValue());
        System.out.println("intValue() :" + num.intValue());
        System.out.println("longValue() :" + num.longValue());*/
        //System.out.println(2<<3);
        Instant instant = Instant.now();
        System.out.println();
    }

    public static long bytesToLong(byte[] b) {
        long l = ((long) b[0] << 56) & 0xFF00000000000000L;
        // 如果不强制转换为long，那么默认会当作int，导致最高32位丢失
        l |= ((long) b[1] << 48) & 0xFF000000000000L;
        l |= ((long) b[2] << 40) & 0xFF0000000000L;
        l |= ((long) b[3] << 32) & 0xFF00000000L;
        l |= ((long) b[4] << 24) & 0xFF000000L;
        l |= ((long) b[5] << 16) & 0xFF0000L;
        l |= ((long) b[6] << 8) & 0xFF00L;
        l |= (long) b[7] & 0xFFL;
        return l;
    }




}
