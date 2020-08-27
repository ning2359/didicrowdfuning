package com.didi.corwd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableFeignClients("com.didi.crowd.api")
@EnableEurekaClient
@SpringBootApplication
public class PayMain {
    public static void main(String[] args) {
        SpringApplication.run(PayMain.class,args);
    }
}
