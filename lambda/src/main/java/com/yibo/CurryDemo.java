package com.yibo;

import java.util.function.Function;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 18:21
 * @Description:
 *
 * 级联表达式和柯里化
 * 柯里化：把多个参数的函数转换为只有一个参数的函数
 * 柯里化的目的：将函数标准化
 * 高阶函数：返回函数的函数
 */
public class CurryDemo {

    public static void main(String[] args) {
        //实现了x+y的级联表达式
        Function<Integer,Function<Integer,Integer>> function = x -> y -> x + y;
        System.out.println(function.apply(2).apply(3));

        Function<Integer,Function<Integer,Function<Integer,Integer>>> function2 = x -> y -> z -> x + y + z;
        System.out.println(function2.apply(2).apply(3).apply(4));

        int[] nums = {2,3,4};
        Function fun = function2;
        for (int num : nums) {
            if(fun instanceof Function){
                Object obj = fun.apply(num);
                if(obj instanceof Function){
                    fun = (Function)obj;
                }else{
                    System.out.println("调用结束：结果为：" + obj);
                }
            }
        }
    }
}
