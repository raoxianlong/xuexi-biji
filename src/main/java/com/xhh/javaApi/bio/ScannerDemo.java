package com.xhh.javaApi.bio;


import org.junit.Test;

import java.io.File;
import java.util.Scanner;

/**
 *  扫描流
 */
public class ScannerDemo {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入内容:");
        if (scanner.hasNext()){
            System.out.println("输入的内容是:" + scanner.nextLine());
        }
    }

}
