package com.xhh.javaApi.bio;


import java.io.*;

/**
 *  序列化
 */
public class SerializeDemo {

    static File file = new File("D:" + File.separator + "raoxianlong");

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 序列化
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file + File.separator + "book.txt"));
        Book book = new Book("Java编程思想", 58.8);
        out.writeObject(book);
        out.close();
        // 反序列化
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file + File.separator + "book.txt"));
        Book book1 = (Book) in.readObject();
        System.out.println(book1);
    }

}

class Book implements Serializable{

    String name;
    double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

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
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
