package com.yibo;

import com.yibo.framework.interfaces.ProxyCreator;
import com.yibo.framework.interfaces.proxy.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 15:31
 * @Description:
 */

@SpringBootApplication
public class WebfluxClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebfluxClientApplication.class, args);
    }

    /**
     * 创建jdk代理工具
     * @return
     */
    @Bean
    public JDKProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }

    @Bean
    public FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator){
        return new FactoryBean<IUserApi>() {
            /**
             * 返回的代理对象
             * @return
             * @throws Exception
             */
            @Override
            public IUserApi getObject() throws Exception {
                return (IUserApi)proxyCreator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
}
