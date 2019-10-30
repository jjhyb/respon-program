package com.yibo.framework.resthandler;

import com.yibo.framework.exception.WebClientRestHandlerException;
import com.yibo.framework.bean.MethodInfo;
import com.yibo.framework.bean.ServerInfo;
import com.yibo.framework.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 17:19
 * @Description:
 */
public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    /**
     * 初始化webClient
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {

        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理rest请求
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        //返回结果
        Object result = null;
        WebClient.RequestBodySpec request = this.webClient
                //请求方法
                .method(methodInfo.getMethod())
                //请求url和参数
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                //请求参数格式,json
                .accept(MediaType.APPLICATION_JSON);

        //判断是否带了body
        if(methodInfo.getBody() != null){
            request.body(methodInfo.getBody(),methodInfo.getBodyElementType());
        }

        //发出请求
        WebClient.ResponseSpec responseSpec = request.retrieve();

        //处理异常
        responseSpec.onStatus(status -> status.value()==404,response -> Mono.just(new WebClientRestHandlerException(404,"Not Found")));

        //处理body
        if(methodInfo.isReturnFlux()){
            result = responseSpec.bodyToFlux(methodInfo.getReturnElementType());
        }else {
            result = responseSpec.bodyToMono(methodInfo.getReturnElementType());
        }


        return result;
    }
}
