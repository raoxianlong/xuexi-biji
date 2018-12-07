package com.xhh.javaApi.enumApi;

public enum  EnumWithSwitch {

    A, B, C;

    public static void main(String[] args) {

        EnumWithSwitch e = EnumWithSwitch.A;

        switch (e) {
            case A:
                System.out.println("this is A");
                break;
            case B:
                System.out.println("this is B");
                break;
            case C:
                System.out.println("this is c");
                break;
        }
    }
}


