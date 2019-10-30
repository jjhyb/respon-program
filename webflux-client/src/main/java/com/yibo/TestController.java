package com.yibo;

import com.yibo.domain.User;
import com.yibo.framework.exception.WebClientRestHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: huangyibo
 * @Date: 2019/10/30 14:27
 * @Description:
 */

@RestController
public class TestController {

    /**
     * 直接注入定义的接口
     */
    @Autowired
    private IUserApi userApi;

    @GetMapping("/test1")
    public void testGetAllUser(){
        //测试提取信息
        //这里不订阅就不会实际发出请求，但会进入我们的代理类
//        userApi.getAllUser();
//        userApi.getUserById("111111");
//        userApi.createUser(Mono.just(User.builder().name("张无忌").age(20).build()));
//        userApi.deleteUserById("111111");

        //直接调用，实现调用rest接口的效果
        Flux<User> users = userApi.getAllUser();
        users.subscribe(System.out::println);
    }

    @GetMapping("/test2")
    public void testGetUserById(){
        //测试提取信息
        //这里不订阅就不会实际发出请求，但会进入我们的代理类
//        userApi.getAllUser();
//        userApi.getUserById("111111");
//        userApi.createUser(Mono.just(User.builder().name("张无忌").age(20).build()));
//        userApi.deleteUserById("111111");

        //直接调用，实现调用rest接口的效果
        String id = "5db87283d540590ddc9b92d0";
        Mono<User> userMono = userApi.getUserById(id);
        userMono.subscribe(user -> {
            System.out.println("getUserById结果：" + user);
        },e -> {
            if(e instanceof WebClientRestHandlerException){
                WebClientRestHandlerException exception = (WebClientRestHandlerException)e;
                String errorMsg = exception.getCode() + ": " + exception.getMessage();
                System.err.println("找不到用户：" + errorMsg);
            }else {
                System.err.println("调用失败：" + e.getMessage());
            }

        });
    }

    @GetMapping("/test3")
    public void testDeleteUserById(){
        //测试提取信息
        //这里不订阅就不会实际发出请求，但会进入我们的代理类
//        userApi.getAllUser();
//        userApi.getUserById("111111");
//        userApi.createUser(Mono.just(User.builder().name("张无忌").age(20).build()));
//        userApi.deleteUserById("111111");

        //直接调用，实现调用rest接口的效果
        String id = "5db87283d540590ddc9b92d0";
        Mono<Void> userMono = userApi.deleteUserById(id);
        userMono.subscribe(user -> {
            System.out.println("deleteUserById：success");
        });
    }

    @GetMapping("/test4")
    public void testcreateUser(){
        //测试提取信息
        //这里不订阅就不会实际发出请求，但会进入我们的代理类
//        userApi.getAllUser();
//        userApi.getUserById("111111");
//        userApi.createUser(Mono.just(User.builder().name("张无忌").age(20).build()));
//        userApi.deleteUserById("111111");

        //直接调用，实现调用rest接口的效果
        User user = new User();
        user.setName("韦小宝");
        user.setAge(28);
        Mono<User> userMono = userApi.createUser(Mono.just(user));
        userMono.subscribe(u -> {
            System.out.println("createUser："+u);
        });
    }
}
