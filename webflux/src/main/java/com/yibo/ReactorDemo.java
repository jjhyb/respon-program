package com.yibo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 15:12
 * @Description:
 */
public class ReactorDemo {

    public static void main(String[] args) throws ClassCastException {
        //reactor = jdk8 stream + jdk9reactive stream
        //Mono 表示0-1个元素
        //flux 表示0-N个元素
        String[] strs = {"1","2","3"};

        //2、定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                //保存订阅关系，需要用它来给发布者响应
                this.subscription = subscription;

                //请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                //接收到一个数据，处理
                System.out.println("接收到数据："+item);

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //处理完调用request在请求一个数据
                this.subscription.request(1);

                //或者 已经达到了目标，调用cancel告诉发布者不在接收数据了
                //this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                //出现了异常(例如处理数据的时候产生了异常)
                throwable.printStackTrace();

                //我们可以告诉发布者，后面不接收数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                //全部数据处理完毕(发布者关闭了)
                System.out.println("处理完了");
            }
        };

        //这里就是jdk8的stream
        Flux .fromArray(strs).map(s -> Integer.parseInt(s))
        //最终操作
        //这里就是jdk9的reactive stream
        .subscribe(subscriber);
    }
}
