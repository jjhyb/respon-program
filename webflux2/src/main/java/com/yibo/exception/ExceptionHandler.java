package com.yibo.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 0:48
 * @Description: RouterFunction编程模式的异常处理
 */

@Component
@Order(-2)//数值越小，优先级越高
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        //设置响应头
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //异常信息
        String errorMsg = toStr(throwable);
        DataBuffer dataBuffer = response.bufferFactory().wrap(errorMsg.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private String toStr(Throwable throwable) {
        //已知异常
        if(throwable instanceof CheckException){
            CheckException checkException = (CheckException)throwable;

            return checkException.getFieldName() +"错误参数：" + checkException.getFieldValue();
        }else {
            //未知异常，需要打印堆栈，方便定位问题
            throwable.printStackTrace();
            return throwable.toString();
//            throwable.getMessage();
        }
    }
}
