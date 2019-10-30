package com.yibo.framework.interfaces.proxy;

import com.yibo.framework.ApiServer;
import com.yibo.framework.bean.MethodInfo;
import com.yibo.framework.bean.ServerInfo;
import com.yibo.framework.interfaces.ProxyCreator;
import com.yibo.framework.interfaces.RestHandler;
import com.yibo.framework.resthandler.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 15:42
 * @Description: 使用jdk动态代理实现代理类
 */

@Slf4j
public class JDKProxyCreator implements ProxyCreator {

    @Override
    public Object createProxy(Class<?> type) {
        log.info("JDKProxyCreator.createProxy:{}",type);
        //根据接口得到Api服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("JDKProxyCreator.createProxy serverInfo:{}",serverInfo);
        //给每一个代理类一个实现
        RestHandler restHandler = new WebClientRestHandler();
        //初始化服务器信息(初始化webClient)
        restHandler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                    new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //根据方法和参数得到调用信息
                MethodInfo methodInfo = extractServerInfo(method,args);
                log.info("JDKProxyCreator.createProxy methodInfo:{}",methodInfo);
                //调用rest
                return restHandler.invokeRest(methodInfo);

            }
        });
    }

    /**
     * 根据方法定义和调用参数得到调用的相关信息
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractServerInfo(Method method,Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        //得到请求url和请求方法
        this.extractUrlAndMethod(method,methodInfo);

        //得到调用的参数和body
        this.extractRequestParamAndBody(method,args,methodInfo);

        //提取返回对象信息
        this.extractReturnInfo(method,methodInfo);

        //得到返回对象的实际类型
        Class<?> elementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(elementType);
        return methodInfo;
    }

    /**
     * 得到泛型类型的实际类型
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        Class<?> elementType =  (Class<?>) actualTypeArguments[0];
        return elementType;
    }

    /**
     * 提取返回对象信息
     *
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        //返回flux还是mono
        //isAssignableFrom()判断类型是否是某个的子类
        //instanceof 判断实例
        boolean isFlux = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(isFlux);
    }

    /**
     * 得到请求的param和body
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractRequestParamAndBody(Method method, Object[] args, MethodInfo methodInfo) {
        Parameter[] parameters = method.getParameters();
        //参数和值对应的map
        Map<String,Object> params = new LinkedHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            //判断参数上面是否带有@PathVariable
            PathVariable pathVariableAnno = parameters[i].getAnnotation(PathVariable.class);
            if(pathVariableAnno != null){
                params.put(pathVariableAnno.value(),args[i]);
            }

            //判断参数上面是否带有@RequestBody
            RequestBody requestBodyAnno = parameters[i].getAnnotation(RequestBody.class);
            if(requestBodyAnno != null){
                //请求体
                methodInfo.setBody((Mono<?>) args[i]);
                //请求对象的实际类型
                methodInfo.setBodyElementType(this.extractElementType(parameters[i].getParameterizedType()));
            }
        }
        methodInfo.setParams(params);
    }

    /**
     * 得到请求的URL和方法
     *
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof GetMapping){
                GetMapping getMethod = (GetMapping)annotation;
                methodInfo.setUrl(getMethod.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }else if(annotation instanceof PostMapping){
                PostMapping postMethod = (PostMapping)annotation;
                methodInfo.setUrl(postMethod.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }else if(annotation instanceof PutMapping){
                PutMapping putMethod = (PutMapping)annotation;
                methodInfo.setUrl(putMethod.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }else if(annotation instanceof DeleteMapping){
                DeleteMapping deleteMethod = (DeleteMapping)annotation;
                methodInfo.setUrl(deleteMethod.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        }
    }

    /**
     * 提取IUserApi接口上的服务器信息
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiServer apiServer = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(apiServer.value());
        return serverInfo;
    }
}
