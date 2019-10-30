package com.yibo.util;

import com.yibo.exception.CheckException;

import java.util.stream.Stream;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 19:22
 * @Description:
 */
public class CheckUtil {

    private static final String[] INVALID_NAMES = {"admin","guanliyuan"};

    /**
     * 校验名字不能有特殊字符且不能为管理员，不成功抛出校验异常
     * @param value
     */
    public static void checkName(String value){
        Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
                .findAny().ifPresent(name -> {
                    throw new CheckException("name",value);
        });
    }
}
