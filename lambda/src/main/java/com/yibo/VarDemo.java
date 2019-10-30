package com.yibo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 18:11
 * @Description: 变量引用
 */
public class VarDemo {

    public static void main(String[] args) {
        String str = "world";
        List<String> list = new ArrayList<>();

        Consumer<String> consumer = s -> System.out.println(s + list);
        consumer.accept("hello ");

    }
}
