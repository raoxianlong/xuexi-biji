package com.xhh.javaApi.annotationApi;


import java.lang.annotation.*;

@Repeatable(Annotation2.class)
public @interface AnnotationFirstDemo {

}

@interface  Annotation2{
    AnnotationFirstDemo[] value();
}


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface  Annotation3{

    // 可以通过default 指定默认值
    String id() default "0";

    /*// 可以是8种数据类型, 但是其包装类型不行
    // 如果注解仅仅只存在value 一个属性，那么在用的时候则不需要指定属性名
    int value();
    //Integer ABc();

    double b();
    //Double  B();

    boolean c();
    //Boolean C();

    short d();
    //Short D();*/

}
enum MyEnum1{

    GREE;

}


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Annotation4{
    // 数组类型属性
    int[] array() default {1, 2, 3};
    // 枚举类型属性
    MyEnum1 e();
}

@Annotation3
class AnnotationClassP{

}


@Annotation4(e = MyEnum1.GREE)
class AnnotationClass extends AnnotationClassP{

    public static void main(String[] args) {
        Class<?> clazz = AnnotationClass.class;
       Annotation[] annotations =  clazz.getAnnotations();
       System.out.println("getAnnotations ：");
       for (Annotation a : annotations){
           if ( a instanceof Annotation4){
               System.out.println(((Annotation4)a).e().name());
           }
           if (a instanceof  Annotation3){
               System.out.println(((Annotation3)a).id());
           }
       }
        Annotation4 annotation = clazz.getAnnotation(Annotation4.class);
        System.out.println(annotation.array());
        System.out.println(clazz.isAnnotationPresent(Annotation4.class));
        //返回直接的注解，不包括父类的
        System.out.println("getDeclaredAnnotations ：");
        Annotation[] annotations1 =  clazz.getDeclaredAnnotations();
        for (Annotation a : annotations){
            if ( a instanceof Annotation4){
                System.out.println(((Annotation4)a).e().name());
            }
            if (a instanceof  Annotation3){
                System.out.println(((Annotation3)a).id());
            }
        }
    }
}








