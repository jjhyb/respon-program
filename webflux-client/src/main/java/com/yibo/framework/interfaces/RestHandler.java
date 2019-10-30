package com.yibo.framework.interfaces;

import com.yibo.framework.bean.MethodInfo;
import com.yibo.framework.bean.ServerInfo;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 16:12
 * @Description: rest请求调用handler
 */
public interface RestHandler {


    /**
     * 初始化服务器信息(初始化webClient)
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用rest请求，返回结果
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
