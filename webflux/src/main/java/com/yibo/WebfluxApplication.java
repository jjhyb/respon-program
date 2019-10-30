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
public class WebfluxApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebfluxApplication.class, args);
    }
}
