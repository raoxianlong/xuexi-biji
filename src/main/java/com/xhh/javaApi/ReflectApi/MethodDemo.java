package com.xhh.javaApi.ReflectApi;

import com.xhh.javaApi.utils.StringUtil;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodDemo {

    @Test
    public void getMethod() throws Exception {
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.eagle");
        // getDeclaredMethods()
        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods){
            System.out.println(m.getName());
        }
        Method method = clazz.getMethod("getName");
        System.out.println(method);
    }

    @Test
    public void getMethod2() throws Exception{
        Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.eagle");

        Method[] methods = clazz.getMethods();

        for (Method m : methods){
            System.out.println(m.getName());
        }
        Method method = clazz.getMethod("getName");
        System.out.println(method.getName());
    }

    /**
     * 测试方法调用
      */
    @Test
    public void callMethod() throws Exception{
        String className = "com.xhh.javaApi.ReflectApi.eagle";
        String attr = "name";
        String val = "老鹰";

        Class<?> clazz = Class.forName(className);

        Object obj  = clazz.newInstance();

        //先设置值
        Method method = clazz.getMethod("set" + StringUtil.initcap(attr), String.class);
        method.invoke(obj, val);
        System.out.println("设置的值是：" + obj);

        //获取值
        Method getMethod = clazz.getMethod("get" + StringUtil.initcap(attr));
        Object getVal = getMethod.invoke(obj);
        System.out.println("获取的值是：" + getVal);
    }

    /**
     * 查看方法信息
     */
    @Test
    public void methodInfo() throws Exception{
         Class<?> clazz = Class.forName("com.xhh.javaApi.ReflectApi.eagle");
         Method[] methods = clazz.getDeclaredMethods();

         for (int i=0; i < methods.length; i++){
             // 修饰符
             System.out.print(Modifier.toString(methods[i].getModifiers()) +" ");
             //返回值类型
             Class<?> returnType = methods[i].getReturnType();
             System.out.print(returnType.getSimpleName() + " ");
             //方法名称
             System.out.print(methods[i].getName());
             //方法参数
             System.out.print("(");
             Class<?>[] paramTypes = methods[i].getParameterTypes();
             for (int y=0; y< paramTypes.length ; y++){
                 System.out.print(paramTypes[y].getSimpleName());
                 if (y < paramTypes.length - 1){
                     System.out.print(", ");
                 }
             }
             System.out.print(")");
            //异常信息
             Class<?>[] exceptions = methods[i].getExceptionTypes();
             if (exceptions.length > 0 ){
                 System.out.print(" throws ");
             }
             for (int j=0; j < exceptions.length; j++){
                 System.out.print(exceptions[j].getSimpleName());
                  if ( j < exceptions.length -1){
                      System.out.print(", ");
                  }
             }
             System.out.println();
         }
    }
}

/**
 * 飞
 */
interface Fly{
    public void fly();
}
/**
 * 鸟
 */
abstract class Bird implements Fly{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 老鹰
 */
class eagle extends Bird{

    @Override
    public void fly() throws  RuntimeException, NullPointerException{

    }

    public static final String getMeat (String name, String size) throws  RuntimeException, NullPointerException{
        return null;
    }

    @Override
    public String toString(){
        return "eagle {name=" + getName() +  "}";
    }

}
