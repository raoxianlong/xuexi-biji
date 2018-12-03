package com.xhh.javaApi;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class RandomDemo {


    /**
     * 随机生成10个随机整数
     */
    @Test
    public void random10(){
        Random rd = new Random();
        int[] num10 = new int[10];
        for (int n = 0 ; n < 10 ;n++){
            num10[n] = rd.nextInt();
        }
        displayArray(num10);
    }

    /**
     * 随机生成(1-37)之间7个不重复的随机整数, 且不能有0
     */
    @Test
    public void random10WithNoRepeat(){
        Random rd = new Random();
        int[] num7 = new int[7];

        for (int n = 0 ; n < 7 ;n++){
            int num  = rd.nextInt(37);
            while (isRepeat(num7, num)){
                num = rd.nextInt(37);
            }
            num7[n] = num;
        }
        displayArray(num7);
    }

    /**
     * 查看是否有重复数
     * @param array
     * @param num
     */
    private static boolean isRepeat(int[] array, int num){
        if (num ==0 ){
            return true;
        }
        for (int i = 0; i < array.length;i++ ){
            if (array[i]==num){
                return true;
            }
         }
         return false;
    }

    /**
     * 打印数组
     * @param array
     */
    private static void displayArray(int[] array){
        Arrays.sort(array);
        StringBuilder sb  = new StringBuilder();
        for (int i=0 ; i<array.length ; i++){
            sb.append(array[i]).append(", ");
        }
        System.out.println(sb.substring(0, sb.length() - 2 ));
    }


}
