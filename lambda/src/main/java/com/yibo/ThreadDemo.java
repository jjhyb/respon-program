package com.yibo;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 14:23
 * @Description:
 */
public class ThreadDemo {

    public static void main(String[] args) {
//        demo1();
        Runnable target = new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        };
        new Thread(target).start();

        //jdk8 Lambda
        Runnable target2 = () -> System.out.println("ok");
        Object target3 = (Runnable)() -> System.out.println("ok");
        System.out.println(target2 == target3);//false
        new Thread(target2).start();
    }

    private static void demo1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        }).start();

        //jdk8 Lambda
        new Thread(() -> System.out.println("ok")).start();
    }
}
