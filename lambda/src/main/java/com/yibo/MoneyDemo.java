package com.yibo;

import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 14:59
 * @Description:
 */


class MyMoney{
    private final int money;
    public MyMoney(int money){
        this.money = money;
    }

    public void printMoney(Function<Integer,String> moneyFormat){
        System.out.println("我的存款：" + moneyFormat.apply(this.money));
    }
}

public class MoneyDemo {

    public static void main(String[] args) {
        MyMoney myMoney = new MyMoney(100000000);

        Function<Integer,String> moneyFormat = i -> new DecimalFormat("#,###").format(i);
        //函数式接口，链式操作
        myMoney.printMoney(moneyFormat.andThen(s -> "人名币" + s));

//        myMoney.printMoney(i -> new DecimalFormat("#,###").format(i));
    }
}
