package com.xhh.javaApi;

public class CharacterDemo {



    public static void main(String[] args) {


        Character ch = 'a'; //自动装箱

        System.out.println(" [判断是否是字母] isLetter(char ch) : " + Character.isLetter(ch));
        System.out.println(" [判断是否是数字] isDigit(char ch) : " + Character.isDigit(ch));
        System.out.println(" [判断是否是空格] isWhitespace(char ch) : " + Character.isWhitespace(ch));
        System.out.println(" [判断是否是大写字母] isUpperCase(char ch) : " + Character.isUpperCase(ch));
        System.out.println(" [判断是否是小写字母] isLowerCase(char ch) : " + Character.isLowerCase(ch));
        System.out.println(" [如果是小写字母则转成大写] toUpperCase(char ch) : " + Character.toUpperCase(ch));
        System.out.println(" [如果是大写字母则转成小写] toLowerCase(char ch) : " + Character.toLowerCase(ch));
    }


}
