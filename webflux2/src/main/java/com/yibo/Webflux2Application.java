package com.yibo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 15:31
 * @Description:
 */

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Webflux2Application {

    public static void main(String[] args) {

        SpringApplication.run(Webflux2Application.class, args);
    }
}
