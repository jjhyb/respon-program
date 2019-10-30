package com.yibo;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 14:32
 * @Description:
 */

@FunctionalInterface
interface Interface1{
    int doubleNum(int i);

    default int add(int x,int y){
        x = this.doubleNum(x);
        return x + y;
    }
}

@FunctionalInterface
interface Interface2{
    int doubleNum(int i);

    default int add(int x,int y){
        x = this.doubleNum(x);
        return x + y;
    }
}

@FunctionalInterface
interface Interface3 extends Interface1,Interface2{

    @Override
    default int add(int x, int y) {
        return 0;
    }
}

public class LambdaDemo1 {

    public static void main(String[] args) {
        Interface1 inter1 = (i) -> i*2;
        System.out.println(inter1.add(3, 4));
        System.out.println(inter1.doubleNum(20));;

        //这种是最常见写法
        Interface1 inter2 = i -> i*2;

        Interface1 inter3 = (int i) -> i*2;

        Interface1 inter4 = (int i) -> {return 1*2;};
    }
}
