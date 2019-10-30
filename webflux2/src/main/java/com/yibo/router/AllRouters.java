package com.yibo.router;

import com.yibo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 0:13
 * @Description:
 */

@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler){
        return RouterFunctions.nest(
                //相当于@RequestMapping("/user")
                RequestPredicates.path("/user"),
                //相当于@GetMapping("/")
                //获取所有用户
                RouterFunctions.route(RequestPredicates.GET("/"),
                        userHandler::getAllUser)
                //创建用户
                .andRoute(RequestPredicates.POST("/")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        ,userHandler::createUser)
                //删除用户
                .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUserById)
        );

    }
}
