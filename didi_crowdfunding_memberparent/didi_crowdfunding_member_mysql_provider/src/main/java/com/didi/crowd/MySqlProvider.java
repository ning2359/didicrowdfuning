package com.didi.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.didi.crowd.mapper")
@SpringBootApplication
public class MySqlProvider {
    public static void main(String[] args) {
        SpringApplication.run(MySqlProvider.class,args);
    }
}
