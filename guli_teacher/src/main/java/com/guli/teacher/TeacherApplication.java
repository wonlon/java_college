package com.guli.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients //通过Feign进行api的调用，在调用者的启动类增加这个注解
public class TeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }
}


