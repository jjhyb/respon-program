package com.yibo;

import java.util.stream.IntStream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 21:00
 * @Description:
 */
public class StreamDemo1 {

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        //外部迭代
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        System.out.println("累加结果：" + sum);

        //使用Stream的内部迭代
        //map就是中间操作(返回stream的操作)
        //sum就是终止操作
        int sum2 = IntStream.of(nums).map(i -> i*2).sum();
        System.out.println("累加结果：" + sum2);

        System.out.println("惰性求职就是终止操作没有调用的情况下，中间操作不会执行");
        IntStream.of(nums).map(i -> i*2);

    }

    public static int doubleNum(int i){
        System.out.println("执行了乘以2");
        return i * 2;
    }
}
