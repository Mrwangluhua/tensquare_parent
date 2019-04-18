package com.tensquare.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class CommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class,args);
    }

    @Bean
    public IdWorker creatIdWorker(){
        return new IdWorker(1,1);
    }
}
