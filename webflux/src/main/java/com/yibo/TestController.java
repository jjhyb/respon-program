package com.yibo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 14:49
 * @Description:
 */

@RestController
@Slf4j
public class TestController {

    @GetMapping("/1")
    public String get1(){
        log.info("get1 start");
        String result = createStr();
        log.info("get1 end");
        return result;
    }

    @GetMapping("/2")
    public Mono<String> get2(){
        log.info("get2 start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        log.info("get2 end");
        return result;
    }

    /**
     * Flux：返回0-n个元素
     * 加上这个produces = "text/event-stream" 数据会像流一样一条一条的返回
     * 不加这个produces = "text/event-stream" 数据就像返回集合一样，直接一次性返回
     *
     * MediaType.TEXT_EVENT_STREAM_VALUE == "text/event-stream"
     * @return
     */
//    @GetMapping(value="/3",produces = "text/event-stream")
    @GetMapping(value="/3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux(){

        Flux<String> result = Flux.fromStream(IntStream.range(1,5).mapToObj(i->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data--"+i;
        }));
        return result;
    }

    @GetMapping("/4")
    public Mono<String> get4(){
        log.info("get4 start");
        String result = createStr();
        log.info("get4 end");
        return Mono.just(result);
    }

    private String createStr(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some String";
    }
}
