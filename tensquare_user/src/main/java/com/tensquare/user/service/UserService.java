package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<User> findAll() {
        return userDao.findAll();
    }

    //发送手机验证码
    public void sendsms(String mobile) {
        //1.生成验证码
        Random random = new Random();
        int code  = random.nextInt(900000)+100000;
        System.out.println("手机号是："+mobile);
        System.out.println("验证码是："+code);
        System.out.println("----------------------");
        //2.把验证码保存到Redis中和mq中   设置有效时间5分钟
        redisTemplate.opsForValue().set("sms_"+mobile,code,5, TimeUnit.MINUTES);
        //3.把手机号和验证码通过rabbitmq发送出去
        Map<String,Object> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("code",code);
        rabbitTemplate.convertAndSend("sms",map);
    }

    //用户注册功能
    public void register(User user, String code) {
        //从Redis中获取验证码
        String sysCode = redisTemplate.opsForValue().get("sms_" + user.getMobile()).toString();
        System.out.println(sysCode);
        //判断从Redis中获取的验证码是否为空，如果为空表示用户没有获取过验证码
        if(sysCode==null){
            throw new RuntimeException("请点击获取验证码");
        }
        //判断Redis中的验证码和传递过来的验证码是否一致，
        if(!sysCode.equals(code)){
            throw new RuntimeException("验证码错误");
        }
        //用户验证码正确就进行注册保存
        //给用户的密码进行加密
        user.setPassword(encoder.encode(user.getPassword()));
        //设置用户的初始属性
        user.setId(idWorker.nextId()+"");
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());
        user.setOnline(0L);
        user.setFanscount(0);
        user.setFollowcount(0);
        userDao.save(user);
    }

    public Optional<User> findById(String userId) {
        return userDao.findById(userId);
    }


    public void updateById(User user, String userId) {
        user.setId(userId);
        userDao.save(user);
    }

    public void deleteById(String userId) {
        userDao.deleteById(userId);
    }

    public User login(String mobile, String password) {
        User loginUser = userDao.findByMobile(mobile);
        if(loginUser!=null && encoder.matches(password,loginUser.getPassword())){
            return loginUser;
        }else {
            return null;
        }
    }
}
