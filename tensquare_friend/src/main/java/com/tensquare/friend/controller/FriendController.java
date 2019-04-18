package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;
    // PUT /friend/like/{friendid}/{type} 添加好友或非好友
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        //获取claims ，从request中进行获取
        Claims claims = (Claims) request.getAttribute("roles_user");
        if(claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"没有权限");
        }


        //在添加好友之前应该判断是否已经添加为好友，如果已经添加了好友就不需要再次添加好友
        if ("1".equals(type)){
            //添加好友
            if(friendService.addFriend(claims.getId(),friendid)>0){
                return new Result(false,StatusCode.REPERROR,"不能重复添加好友");
            }
        }else {
            //添加非好友

        }
        return new Result(true, StatusCode.OK,"操作成功");
    }
}
