package com.yibo.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 15:52
 * @Description: 方法调用信息类
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 请求参数
     */
    private Map<String,Object> params;

    /**
     * 请求body
     */
    private Mono body;

    /**
     * 请求body的类型
     */
    private Class<?> bodyElementType;

    /**
     * 返回是flux还是mono
     */
    private boolean returnFlux;

    /**
     * 返回是对象的类型
     */
    private Class<?> returnElementType;
}
