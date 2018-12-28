package com.xhh.javaApi.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 *  buffer Demo
 */
public class BufferDemo {

    @Test
    public void testBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // put 数据
        buffer.put("abc".getBytes());
        System.out.println("----------------------put--------------------------");
        System.out.println("position :" + buffer.position());
        System.out.println("limit :" + buffer.limit());
        System.out.println("capacity :" + buffer.capacity());

        // flip() 切换到数据读取模式
        buffer.flip();
        System.out.println("----------------------flip--------------------------");
        System.out.println("position :" + buffer.position());
        System.out.println("limit :" + buffer.limit());
        System.out.println("capacity :" + buffer.capacity());

        // get() 获取数据
        byte[] temp = new byte[buffer.limit()];
        buffer.get(temp);
        System.out.println("----------------------get--------------------------");
        System.out.println("position :" + buffer.position());
        System.out.println("limit :" + buffer.limit());
        System.out.println("capacity :" + buffer.capacity());
        System.out.println("获取的数据是：" + new String(temp, 0, buffer.limit()));
        // 判断缓冲区是否还有数据
        System.out.println("是否还有数据：" + buffer.hasRemaining());

        // mark() 标记
        buffer.mark();
        // 清楚当前位置  rewind clear 会清除标记的位置
        // buffer.clear();
        // 重复读
        buffer.rewind();
        System.out.println("----------------------rewind--------------------------");
        System.out.println("position :" + buffer.position());
        System.out.println("limit :" + buffer.limit());
        System.out.println("capacity :" + buffer.capacity());

        // 恢复到标记的位置
        buffer.reset();
        System.out.println("----------------------reset--------------------------");
        System.out.println("position :" + buffer.position());
        System.out.println("limit :" + buffer.limit());
        System.out.println("capacity :" + buffer.capacity());

    }



}
