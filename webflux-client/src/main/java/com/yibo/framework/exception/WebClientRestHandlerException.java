package com.yibo.framework.exception;

import lombok.Data;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 18:57
 * @Description:
 */
@Data
public class WebClientRestHandlerException extends RuntimeException {

    /**
     * 校验异常的字段
     */
    private Integer code;

    /**
     * 校验异常的字段值
     */
    private String message;

    public WebClientRestHandlerException(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
