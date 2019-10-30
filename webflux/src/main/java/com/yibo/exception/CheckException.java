package com.yibo.exception;

import lombok.Data;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 19:24
 * @Description:
 */

@Data
public class CheckException extends RuntimeException {

    /**
     * 校验异常的字段
     */
    private String fieldName;

    /**
     * 校验异常的字段值
     */
    private String fieldValue;

    public CheckException(String fieldName,String fieldValue){
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
