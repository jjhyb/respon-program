package com.yibo;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 1:01
 * @Description:
 */
public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {
        //1、定义发布者，发布的数据类型是Integer
        //直接使用jdk自带的SubmissionPublisher，它实现了Publisher接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();

        //2、定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
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

        //3、发布者和订阅者建立订阅关系
        publisher.subscribe(subscriber);

        //4、生产数据，并发布
        //这里忽略数据生产过程
        for (int i=0;i<1000;i++){
            System.out.println("生成数据：" + i);
            //submit是一个block(阻塞)方法 只要订阅者的缓冲池满了，submit方法就被阻塞了，从而调节生产者生产数据的速度
            publisher.submit(i);
        }

        //5、结束后，关闭发布者
        //正式环境应该放在finally或者使用try-resource确保关闭
        publisher.close();

        //主线程延迟停止，否则数据没有消费就退出
        Thread.currentThread().join(1000);
    }
}
