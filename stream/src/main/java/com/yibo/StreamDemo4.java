package com.yibo;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 22:24
 * @Description:
 */
public class StreamDemo4 {

    public static void main(String[] args) {
        String str = "my name is 007";

        //使用并行流，发现下面的打印顺序是乱的
        str.chars().parallel().forEach(i -> System.out.print((char)i));

        System.out.println();

        //使用并行流的同时使用forEachOrdered保证顺序
        str.chars().parallel().forEachOrdered(i -> System.out.print((char)i));

        System.out.println();

        //collect 收集到list
        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list);

        //使用reduce拼接字符串
        Optional<String> letters = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "-" + s2);
        System.out.println(letters.orElse(""));

        //带初始化的reduce
        String reduce = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + "-" + s2);
        System.out.println(reduce);

        //计算所有单词的总长度
        Integer length1 = Stream.of(str.split(" ")).map(s -> s.length()).reduce(0, (s1,s2) -> s1+s2);
        System.out.println(length1);
        Integer length2 = Stream.of(str.split(" ")).map(s -> s.length()).reduce(0, Integer::sum);
        System.out.println(length2);

        //min,max,count
        Optional<String> max1 = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.out.println(max1.get());

        //使用findFirst短路操作
        OptionalInt first = new Random().ints().findFirst();
        System.out.println(first.getAsInt());



    }
}
