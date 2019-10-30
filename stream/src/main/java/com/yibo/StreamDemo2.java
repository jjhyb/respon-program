package com.yibo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 21:22
 * @Description: Stream的创建方式
 */
public class StreamDemo2 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        //从集合创建
        list.stream();
        list.parallelStream();

        //从数组创建
        Arrays.stream(new int[]{1,2,3});

        //创建数字流
        IntStream.of(1,2,3);

        //创建一个包含两端的数值流，比如1到10，包含10：
        IntStream.rangeClosed(1,10);
        //创建一个不包含结尾的数值流，比如1到10：
        IntStream.range(1,10);

        //使用random创建一个无限流
        new Random().ints().limit(10);

        //自己产生流
        Stream.generate(() -> new Random().nextInt()).limit(10);
    }
}
