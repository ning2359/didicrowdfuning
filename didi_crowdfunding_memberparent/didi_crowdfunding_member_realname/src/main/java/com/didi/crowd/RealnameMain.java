package com.didi.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("com.didi.crowd.api")
@EnableEurekaClient
@SpringBootApplication
public class RealnameMain {
    public static void main(String[] args) {
        SpringApplication.run(RealnameMain.class,args);
    }
}
