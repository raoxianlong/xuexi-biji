package com.xhh.javaApi.bio;

import java.io.File;
import java.io.IOException;

public class PrintUtilTest {


    public static void main(String[] args) throws IOException {
//        PrintUtil print = new PrintUtil(System.out);
        PrintUtil print = new PrintUtil(new File("D:" + File.separator + "raoxianlong" + File.separator + "testPrint.txt"));
        print.printf("%88s", "饶先龙是傻逼");
        print.print("a");
        print.print(3);
        print.print("饶先龙是傻逼222");
    }



}
