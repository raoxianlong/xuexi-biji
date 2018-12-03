package com.xhh.javaApi;

import org.junit.Test;

import java.io.IOException;

public class RuntimeDemo {

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        //获取最大内存空间
        System.out.println("JVM中尝试使用的的最大内存空间:" + rt.maxMemory()/(1024 * 1024) + "M");
        //获取JVM中的内存总量
        System.out.println("JVM中的最大内总量:" + rt.totalMemory()/(1024 * 1024) + "M");
        //获取JVM中的剩余的内存空间
        System.out.println("JVM中的剩余空间:" +rt.freeMemory()/(1024 * 1024) + "M");

        //我们使用10M的空间
        byte[] bts = new byte[1024 * 1024 * 10];

        //获取最大内存空间
        System.out.println("JVM中尝试使用的的最大内存空间:" + rt.maxMemory()/(1024 * 1024) + "M");
        //获取JVM中的内存总量
        System.out.println("JVM中的最大内总量:" + rt.totalMemory()/(1024 * 1024) + "M");
        //获取JVM中的剩余的内存空间
        System.out.println("JVM中的剩余空间:" +rt.freeMemory()/(1024 * 1024) + "M");

        //回收一下
        bts = null;
        rt.gc();

        //获取最大内存空间
        System.out.println("JVM中尝试使用的的最大内存空间:" + rt.maxMemory()/(1024 * 1024) + "M");
        //获取JVM中的内存总量
        System.out.println("JVM中的最大内总量:" + rt.totalMemory()/(1024 * 1024) + "M");
        //获取JVM中的剩余的内存空间
        System.out.println("JVM中的剩余空间:" +rt.freeMemory()/(1024 * 1024) + "M");

    }

    /**
     * 测试exec方法
     */
    @Test
    public void execDemo() throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec("notepad.exe");
        Thread.sleep(5000);
        process.destroy();
    }

}
