package com.xhh.javaApi;

public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        Book book = new Book("Java编程思想", 88.8);
        Book book1 = (Book) book.clone();

        System.out.println(book);
        System.out.println(book1);
    }
}

class Book implements Cloneable{

    private String name;
    private double price;

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
