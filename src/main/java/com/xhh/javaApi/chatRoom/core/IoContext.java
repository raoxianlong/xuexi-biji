package com.xhh.javaApi.chatRoom.core;

import java.io.IOException;

public class IoContext {
    private static IoContext INSTANCE;
    private final IoProvider ioProvider;

    public IoContext(IoProvider ioProvider) {
        this.ioProvider = ioProvider;
    }

    public static IoContext get(){
        return INSTANCE;
    }

    public IoProvider getIoProvider(){
        return this.ioProvider;
    }

    public static StartedBoot setup(){
        return new StartedBoot();
    }

    public static void close() throws IOException {
        if (INSTANCE != null){
            INSTANCE.callClose();
        }
    }

    private void callClose() throws IOException {
        ioProvider.close();
    }

    public static class StartedBoot{
        private IoProvider ioProvider;

        public StartedBoot() {
        }

        public StartedBoot ioProvider(){
            this.ioProvider = ioProvider;
            return this;
        }

        public IoContext start(){
            INSTANCE = new IoContext(ioProvider);
            return INSTANCE;
        }
    }

}
