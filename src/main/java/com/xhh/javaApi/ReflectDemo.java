package com.xhh.javaApi;


/**
 * 反射
 */
public class ReflectDemo {

    public static void main(String[] args){
        /*try {
            Class<?> clazz  = Class.forName("com.xhh.javaApi.Book1");
            Object object = clazz.newInstance();
            System.out.println(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 类找不到
        } catch (IllegalAccessException e) {
            e.printStackTrace(); // 构造方法私有化
        } catch (InstantiationException e) {
            e.printStackTrace(); //没有构造方法、类名错误
        }*/
        Msg msg = new Emails();
        msg.print("今天没下雨");



    }
}


interface Msg{
    public void print(String msg);
}
class News implements Msg{
    public void print(String msg) {
        System.out.println( "新闻消息：" + msg);
    }
}
class Emails implements Msg{
    public void print(String msg) {
        System.out.println( "邮件消息：" + msg);
    }
}

/**
 * 简单工厂
 */
class SimpleFactory{

    public static Msg getNewInstance(String className){
        if ("News".equalsIgnoreCase(className)){
            return  new News();
        }else if("Emails".equalsIgnoreCase(className)){
            return new Emails();
        }
        return null;
    }
}

class Book1 {

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book1{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
