package com.yibo.framework.interfaces;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 15:35
 * @Description: 创建代理类接口
 */
public interface ProxyCreator {

    /**
     * 创建代理类
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}
