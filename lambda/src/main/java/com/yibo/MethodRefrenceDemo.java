package com.yibo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 15:34
 * @Description:
 */
public class MethodRefrenceDemo {

    public static void main(String[] args) {
        //方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("你好啊");

        //静态方法的方法引用
        Consumer<Dog> dogConsumer = Dog::bark;
        dogConsumer.accept(new Dog());

        //非静态方法，使用对象实例的方法引用
        //下面三个写法都一样
//        Function<Integer,Integer> function = new Dog()::eat;
//        UnaryOperator<Integer> function = new Dog()::eat;
//        System.out.println("还剩下"+function.apply(2)+"斤狗粮");

        IntUnaryOperator function = new Dog()::eat;
        System.out.println("还剩下"+function.applyAsInt(2)+"斤狗粮");

        new Dog().eat(1);
        //使用类名来引用非静态方法
        BiFunction<Dog,Integer,Integer> biFunction  = Dog::eat;
        System.out.println("还剩下"+biFunction.apply(new Dog(),2)+"斤狗粮");

        //构造函数的方法引用
        Supplier<Dog> supplier = Dog::new;
        System.out.println("创建了新对象："+supplier.get());

        //带参数的构造函数的方法引用
        Function<String,Dog> funcion2 = Dog::new;
        System.out.println("创建了新对象："+funcion2.apply("旺财"));

        MethodRefrenceDemo methodRefrenceDemo = new MethodRefrenceDemo();

        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        methodRefrenceDemo.test(list);
        System.out.println(list);

    }

    //
    public static void test(List<String> list){
        list.add("lisi");
        list.remove("zhangsan");
        //上面两行代码主方法中的list或受影响

        //下面一行代码不会影响主方法中的list
        list = null;
    }
}

class Dog{

    private String name = "哮天犬";

    /**
     * 默认10斤狗粮
     */
    private int food = 10;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    public Dog(String name, int food) {
        this.name = name;
        this.food = food;
    }

    public static void bark(Dog dog){
        System.out.println(dog + "叫了");
    }

    /**
     * 吃狗粮
     * JDK默认会把当前实例传入到非静态方法，参数名为this，位置为第一个参数
     * @param num
     * @return 剩下多少斤
     */
    public int eat(Dog this,int num){
        System.out.println("吃了"+num+"斤狗粮");
        return this.food  - num;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}
