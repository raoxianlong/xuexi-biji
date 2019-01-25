package com.xhh.javaApi.nio.udp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 *  使用UDP
 */
public class DatagramChannelServer {

    /**
     *  服务端
     * @param args
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(8890));
        dc.configureBlocking(false);

        Selector selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);

        while (selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()){
                SelectionKey sk =  it.next();

                if (sk.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    DatagramChannel dc1 = (DatagramChannel) sk.channel();
                    dc1.receive(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            it.remove();
        }
    }






}
