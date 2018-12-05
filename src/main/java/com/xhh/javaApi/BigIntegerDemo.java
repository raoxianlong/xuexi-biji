package com.xhh.javaApi;

import java.math.BigInteger;

public class BigIntegerDemo {

    public static void main(String[] args) {
        BigInteger bI1 = new BigInteger("12634684313468768688");
        BigInteger bI2 = new BigInteger("1263468431348");

        System.out.println("加法: "+ (bI1.add(bI2)));
        System.out.println("减法: "+ (bI1.subtract(bI2)));
        System.out.println("乘法: "+ (bI1.multiply(bI2)));
        System.out.println("除法: "+ (bI1.divide(bI2)));
        BigInteger[] bigIntegers = bI1.divideAndRemainder(bI2);
        System.out.println("商： " + bigIntegers[0] + "， 余数：" + bigIntegers[1]);
    }

}
