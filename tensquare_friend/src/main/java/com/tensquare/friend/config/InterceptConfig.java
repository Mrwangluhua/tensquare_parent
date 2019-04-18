package com.tensquare.friend.config;

import com.tensquare.friend.intercept.JwtIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//声明这是一个配置类
@Configuration
//WebMvcConfigurationSupport 用来配置springmvc的相关信息
public class InterceptConfig extends WebMvcConfigurationSupport{

    @Autowired
    private JwtIntercept jwtIntercept;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIntercept) //设置使用哪个拦截器
                .addPathPatterns("/**")        //设置拦截路径，/**表示所有路径都要被拦截
                .excludePathPatterns("/**/login");  //设置不需要拦截的路径  登录不能够被拦截
    }
}
