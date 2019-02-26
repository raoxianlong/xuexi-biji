package com.xhh.javaApi.socket.tcpAndUdp.tcp.handle;



import com.xhh.javaApi.socket.tcpAndUdp.tcp.CloseUtils;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  客户端请求处理类
 *      读数据
 *      发送数据
 *      关闭处理
 */
public class ClientHandler {

    private final Socket socket;
    private final ClientReadHandler readHandler;
    private final ClientWriteHandler writeHandler;
    private final CloseNotify closeNotify;

    public ClientHandler(Socket socket, CloseNotify closeNotify) throws IOException {
        this.socket = socket;
        this.closeNotify = closeNotify;
        this.readHandler = new ClientReadHandler(socket.getInputStream());
        this.writeHandler = new ClientWriteHandler(new PrintStream(socket.getOutputStream()));
    }

    public void exit(){
        readHandler.exit();
        writeHandler.exit();
        System.out.println("客户端已退出:" + socket.getInetAddress()+ ":" + socket.getPort());
        CloseUtils.close(socket);
    }
    public void readToPrint(){
        readHandler.start();
    }

    public void exitBySelf(){
        exit();
        closeNotify.onSelfClosed(this);
    }

    public void send(String str){
        writeHandler.send(str);
    }

    public interface CloseNotify{
        void onSelfClosed(ClientHandler clientHandler);
    }

    class ClientReadHandler extends Thread{
        private boolean done = false;
        private final InputStream inputStream;

        public ClientReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                while (!done){

                        String readStr = bufferedReader.readLine();
                        if (readStr == null){
                             System.out.println("客户端已关闭");
                             ClientHandler.this.exitBySelf();
                             break;
                        }
                        System.out.println(readStr);
                }
            } catch (IOException e) {
                if (!done){
                    System.out.println("连接异常断开");
                    ClientHandler.this.exitBySelf();
                }
                e.printStackTrace();
            }finally {
                CloseUtils.close(inputStream);
            }
        }

        void exit(){
            done = true;
            CloseUtils.close(inputStream);
        }
    }

    class ClientWriteHandler{
        private boolean done = false;
        private final PrintStream printStream;
        private final ExecutorService executorService;

        public ClientWriteHandler(OutputStream outputStream) {
            this.printStream = new PrintStream(outputStream);
            this.executorService = Executors.newSingleThreadExecutor();
        }

        void exit(){
            done = true;
            CloseUtils.close(printStream);
            executorService.shutdownNow();
        }

        void send(String str){
            executorService.execute(new WriteRunnable(str));
        }

        class WriteRunnable implements Runnable{
            private final String msg;

            public WriteRunnable(String msg) {
                this.msg = msg;
            }

            @Override
            public void run() {
                if(ClientWriteHandler.this.done){
                    return;
                }
                try {
                    ClientWriteHandler.this.printStream.println(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
