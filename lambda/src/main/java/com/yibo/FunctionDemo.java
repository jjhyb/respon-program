package com.yibo;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 15:27
 * @Description:
 */
public class FunctionDemo {

    public static void main(String[] args) {
        //断言型函数式接口
        Predicate<Integer> predicate = i -> i > 0;
        System.out.println(predicate.test(-1));

        IntPredicate intPredicate = i -> i < 0;
        System.out.println(intPredicate.test(5));

        //消费型函数接口
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("你好");

        IntConsumer intConsumer = s -> System.out.println(s);
        consumer.accept("你好啊");
    }
}
