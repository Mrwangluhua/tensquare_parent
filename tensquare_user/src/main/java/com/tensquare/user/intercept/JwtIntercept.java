package com.tensquare.user.intercept;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtIntercept implements HandlerInterceptor{
    @Autowired
    private JwtUtil jwtUtil;
    //进入controller方法之前进行拦截
    //在进入controller之前就需要进行鉴权
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("执行拦截操作");
        //获取头信息
        String header = request.getHeader("Authorware");
        //判断header是否为空，判断header是否以bearer加空格开头
        if(header!=null&&header.startsWith("Bearer ")){
            //获取token
            try {
                String token = header.substring(7);
                //获取到权限的claims
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    if ("admin".equals(claims.get("roles"))){
                        request.setAttribute("roles_admin",claims);
                    }
                    if ("user".equals(claims.get("roles"))){
                        request.setAttribute("roles_user",claims);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        //返回false表示拦截，就不会执行controller里面的方法
        //返回true表示不会拦截，后续的controller就会执行
        return true;
    }


    //进入controller方法后，并执行，在返回ModelAndView之前进行拦截
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    //controller 所有该执行的都已经执行完毕后，并且返回了结果，最后再执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
