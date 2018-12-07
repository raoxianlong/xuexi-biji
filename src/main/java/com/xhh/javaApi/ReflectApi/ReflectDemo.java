package com.xhh.javaApi.ReflectApi;


import org.junit.Test;

import java.util.ResourceBundle;

/**
 * 反射
 */
public class ReflectDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        /*try {
            Class<?> clazz  = Class.forName("com.xhh.javaApi.ReflectApi.Book1");
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

        ResourceBundle resourceBundle = ResourceBundle.getBundle("msg");
        String className = resourceBundle.getString("class.name");

        Msg msg1 =  SimpleFactory.getNewInstance(className);
        msg1.print("今天风好大");
    }


    @Test
    public void test(){
        String[] str = {"小明", "小红"};
        System.out.println(str.getClass().getTypeName());
        System.out.println(str.getClass().getSimpleName());
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

    public static Msg getNewInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        /*if ("News".equalsIgnoreCase(className)){
            return  new News();
        }else if("Emails".equalsIgnoreCase(className)){
            return new Emails();
        }
        return null;*/
        Class<?> clazz = Class.forName(className);
        Msg msg = (Msg) clazz.newInstance();
        return msg;

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
