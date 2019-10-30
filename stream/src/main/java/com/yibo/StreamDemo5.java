package com.yibo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 23:17
 * @Description:
 */
public class StreamDemo5 {

    public static void main(String[] args) {
        //调用parallel()产生一个并行流
        //IntStream.range(1,100).parallel().peek(StreamDemo5::debug1).count();


        //现在要实现一个这样的操作：先并行，在串行
        //结论：parallel()/sequential() 以最后一次调用为准
        /*IntStream.range(1,100)
                //调用parallel()产生并行流
                .parallel().peek(StreamDemo5::debug1)
                //调用sequential()产生串行流
                .sequential().peek(StreamDemo5::debug2)
                .count();*/


        //并行流使用的是线程池是ForkJoinPool.commonPool
        //ForkJoinPool.commonPool默认的线程数是当前机器的cpu核心数
        //使用这个属性可以修改默认的线程数
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","20");
        //IntStream.range(1,100).parallel().peek(StreamDemo5::debug1).count();


        //使用自己的线程池，不使用默认线程池，防止任务被阻塞
        //线程名字ForkJoinPool-1
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        forkJoinPool.submit(() -> IntStream.range(1,100).parallel().peek(StreamDemo5::debug1).count());
        forkJoinPool.shutdown();

        //阻塞主线程
        synchronized (forkJoinPool){
            try {
                forkJoinPool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug1(int i){
        System.out.println(Thread.currentThread().getName()+"，debug1 " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void debug2(int i){
        System.err.println("debug2 " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
