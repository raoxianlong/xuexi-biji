package test;

/**
 * Created by 002559 on 2018/9/17.
 */
public class Demo {


    public static void main(String[] args) {
       /* int[] x = new int[3];
        int[] y = x;
        x[0] = 100;
        setArray(y);
        System.out.println("x: ");
        showArray(x);
        System.out.println("y: ");
        showArray(y);*/
        //assertTest(null);
        System.out.println("rxl是傻逼");
    }

    public static void setArray(int[] array){
        array[1] = 200;

        array[1] = 200;
    }

    public  static void assertTest(Integer[] array){
        assert array != null;
        System.out.println("真的");
    }

    public static void showArray(int[] array){

        System.out.print("[");
        for (int item : array){
            System.out.print(item);
            System.out.print(",");
        }
        System.out.println("]");
    }

}
