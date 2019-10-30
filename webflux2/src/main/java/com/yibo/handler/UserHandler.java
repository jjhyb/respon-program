package com.yibo.handler;

import com.yibo.domain.User;
import com.yibo.repository.UserRepository;
import com.yibo.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 23:58
 * @Description:
 */

@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取所有用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request){

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userRepository.findAll(),User.class);
    }

    /**
     * 创建用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request){
        //springBoot 2.0可以工作，但是从2.0.1以后下面这行代码会抛异常，而且这行代码直接将线程阻塞了
        //User user = request.bodyToMono(User.class).block();

        Mono<User> userMono = request.bodyToMono(User.class);

        return userMono.flatMap(user -> {
            //RouterFunction编程模式，校验代码需要放在这里
            CheckUtil.checkName(user.getName());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(userRepository.save(user),User.class);
        });
    }

    /**
     * 根据id删除用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        String id = request.pathVariable("id");

        return userRepository.findById(id).flatMap(user ->
                userRepository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
