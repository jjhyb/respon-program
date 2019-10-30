package com.yibo;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 22:03
 * @Description: Stream的中间操作
 */
public class StreamDemo3 {

    public static void main(String[] args) {
        String str = "my name is 007";

        System.out.println("--------------map-----------------------");
        //把每个单词的长度打印出来
        Stream.of(str.split(" ")).filter(s -> s.length()>2)
                .map(s -> s.length()).forEach(System.out::println);

        System.out.println("--------------flatMap-----------------------");
        //flatMap 适合的是A元素下面有B属性(B属性是个集合)，最终得到所有的A元素里面的所有B属性集合
        //intStream或LongStream等并不是Stream的子类，所以要进行装箱即Stream<Integer> boxed = intStream.boxed();
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(s -> System.out.println((char)s.intValue()));

        System.out.println("--------------peek-----------------------");
        //peek 用于debug，是个中间操作，和forEach是终止操作
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);

        System.out.println("--------------limit-----------------------");
        //limit的使用，主要用于无限流，页用于集合中的数据获取
        new Random().ints().filter(i -> i > 100 && i < 1000).limit(10).forEach(System.out::println);

    }
}
