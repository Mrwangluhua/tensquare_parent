package com.tensquare.qa.client;


import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//声明接口，声明微服务的调用
@FeignClient("tensquare-base")   //声明用feign去调用哪个微服务
public interface LabelClient {


    //根据id查询
    //value是完整的调用地址
    //@PathVariable  注解必须声明使用的是哪个占位符
    @RequestMapping(value = "label/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);

}
