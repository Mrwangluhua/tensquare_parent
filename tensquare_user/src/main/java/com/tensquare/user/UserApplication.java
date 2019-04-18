package com.tensquare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1,1);
    }
    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //实例化 jwt工具类 JwtUtil
    @Bean
    public JwtUtil createJwtUtil(){
        return new JwtUtil();
    }
}
