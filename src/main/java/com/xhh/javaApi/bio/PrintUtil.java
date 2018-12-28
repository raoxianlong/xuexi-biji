package com.xhh.javaApi.bio;


import java.io.*;
import java.util.Formatter;
import java.util.Locale;

/**
 *  自定义打印流
 */
public class PrintUtil extends FilterOutputStream implements Appendable{

    private Formatter formatter;

    public PrintUtil(OutputStream out){
        super(out);
        formatter = new Formatter(this, Locale.getDefault());
    }

    public PrintUtil(File file) throws FileNotFoundException {
        super(new FileOutputStream(file));
        formatter = new Formatter(this, Locale.getDefault());
    }

    private void writer(String str){
        try {
            this.out.write(str.getBytes());
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(int b){
        writer(String.valueOf(b));
    }

    public void print(byte b){
        writer(String.valueOf(b));
    }

    public void print(Character b){
        writer(String.valueOf(b));
    }

    public void print(String str){
        writer(str);
    }

    public void printf(String fmt, Object... vars){
        formatter.format(fmt, vars);
    }

    @Override
    public PrintUtil append(CharSequence csq) throws IOException {
        writer(csq.toString());
        return this;
    }

    @Override
    public PrintUtil append(CharSequence csq, int start, int end) throws IOException {
        writer(csq.subSequence(start, end).toString());
        return this;
    }

    @Override
    public PrintUtil append(char c) throws IOException {
        print(c);
        return this;
    }

}
