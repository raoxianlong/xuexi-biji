package com.xhh.javaApi.ReflectApi;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Constructor类练习
 */
public class ConstructorDemo {

    public static void main(String[] args) {
        Class<?> clazz = Book.class;
        Constructor<?>[] cons = clazz.getDeclaredConstructors();
        for (Constructor<?> con : cons){
            //获取修饰符
            System.out.print(Modifier.toString(con.getModifiers())+ " ");
            //获取构造方法名称
            System.out.print(con.getName());
            //获取方法参数
            System.out.print("(");
            Class<?>[] paramsType = con.getParameterTypes();
            for (int i=0; i <  paramsType.length; i++){
                System.out.print(paramsType[i].getSimpleName() + " arg" + i);
                if (i < paramsType.length - 1){
                    System.out.print(", ");
                }
            }
            System.out.print(")");
            //获取异常
            Class<?>[] exceptions = con.getExceptionTypes();
            if (exceptions.length > 0){
                System.out.print(" throws ");
            }
            for (int i=0; i <  exceptions.length; i++){
                System.out.print(exceptions[i].getSimpleName());
                if (i < paramsType.length - 1){
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    @Test
    public void testInit() throws Exception {
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.Book");
        // 获取私有构造方法
        Constructor<?> con = clazz.getDeclaredConstructor(String.class);
        // 设置可以访问私有构造方法
        con.setAccessible(true);
        //实例化对象
        Object obj = con.newInstance("Java");

        System.out.println(obj);
    }

}
class Book{

    private String name;
    private Double price;

    public Book(String name, Double price)throws RuntimeException, NullPointerException {
        this.name = name;
        this.price = price;
    }
    private Book(String name) {
        this.name = name;
    }
    public Book(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
