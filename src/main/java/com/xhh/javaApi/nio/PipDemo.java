package com.xhh.javaApi.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipDemo {

    @Test
    public void pip() throws IOException, InterruptedException {
        Pipe pipe = Pipe.open();
        MyThread t1 = new MyThread(pipe, 0);
        MyThread t2 = new MyThread(pipe, 1);
        t1.run();
        Thread.sleep(2000);
        t2.run();
        Thread.sleep(2000);
        pipe.sink().close();
        pipe.source().close();
    }
}

class MyThread extends Thread{

    private int flag;
    private Pipe pipe;
    public MyThread(Pipe pipe, int flag){
        this.pipe = pipe;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag == 0){
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Pipe.SinkChannel sinkChannel = pipe.sink();
                buffer.put("饶先龙是傻逼".getBytes());
                buffer.flip();
                sinkChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Pipe.SourceChannel sourceChannel = pipe.source();
                sourceChannel.read(buffer);
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, buffer.limit()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}