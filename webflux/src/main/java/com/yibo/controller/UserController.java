package com.yibo.controller;

import com.yibo.domain.User;
import com.yibo.repository.UserRepository;
import com.yibo.util.CheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 16:30
 * @Description:
 */

@RestController
@RequestMapping("/user")
//@Validated
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll(){
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     * @return
     */
    @GetMapping(value="/stream/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getStreamAll(){
        return userRepository.findAll();
    }

    /**
     * 新增数据
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> createUser(@RequestBody @Validated User user){
        //springdata jpa里面，新增和修改都是save，有id是修改，没id事新增
        user.setId(null);
        //为了测试认为制造的异常
        CheckUtil.checkName(user.getName());
        return userRepository.save(user);
    }

    /**
     * 根据id删除用户
     * 存在的时候返回200，不存在返回404
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
        //deleteById没有返回值，不能判断数据是否存在
        //userRepository.deleteById(id);

        return userRepository.findById(id)
                //当你要操作数据，并返回一个Mono，这个时候使用flatMap
                //如果不操作数据，只是对数据做一个转换使用map
                .flatMap(user -> userRepository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    /**
     * 存在的时候返回200和修改后的数据，不存在返回404
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id,@Valid @RequestBody User user){
        //flatMap操作数据
        return userRepository.findById(id).flatMap(u -> {
            String userId = u.getId();
            BeanUtils.copyProperties(user,u);
            u.setId(userId);
            return userRepository.save(u);
        })
                //map转换数据
                .map(u -> new ResponseEntity<User>(u,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }


    /**
     * 根据id查询用户
     * 存在返回用户信息，不存在返回404
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getOne(@PathVariable("id") String id){
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<User>(user,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        return userRepository.findByAgeBetween(start,end);
    }

    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value="/stream/age/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        return userRepository.findByAgeBetween(start,end);
    }

    /**
     * 得到20-30年龄之间的用户
     * @return
     */
    @GetMapping("/old")
    public Flux<User> oldUser(){
        return userRepository.oldUser();
    }

    /**
     * 得到20-30年龄之间的用户
     * @return
     */
    @GetMapping(value="/stream/old",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamOldUser(){
        return userRepository.oldUser();
    }
}
