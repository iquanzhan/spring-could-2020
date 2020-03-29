package com.chengxiaoxiao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: Cheng XiaoXiao  (🍊 ^_^ ^_^)
 * @Date: 2020/3/29 3:27 下午
 * @Description:
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMain7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMain7002.class, args);
    }
}
