package com.yibo;


import java.util.stream.IntStream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 13:06
 * @Description:
 */
public class Lambda {

    public static void main(String[] args) {
        int[] nums = {33,55,-55,-90,-888,123};

        //命令是编程
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if(num < min){
                min = num;
            }
        }
        System.out.println(min);

        //函数式编程
        //.parallel()流会并行执行，包含多线程，线程池，数据拆分
        int min2 = IntStream.of(nums).parallel().min().getAsInt();
        System.out.println(min2);


    }
}
