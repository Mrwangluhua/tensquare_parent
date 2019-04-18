package com.tensquare.user.comtroller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("all")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    //直接注入当前请求的request，这种写法的作用和直接在controller方法声明request的效果是一样的
    //好处是直接把request变成controller全局的   只需要声明一次即可
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    //GET /user 用户全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<User> userList = userService.findAll();
        return new Result(true, StatusCode.OK, "查询所有成功", userList);
    }

    //GET /user/{userId} 根据ID查询
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String userId) {
        User user = userService.findById(userId).get();
        return new Result(true, StatusCode.OK, "查询所有成功", user);
    }

    //PUT /user/{userId} /修改用户
    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    public Result updateById(@RequestBody User user, @PathVariable String userId) {
        userService.updateById(user, userId);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    //DELETE /user/{userId} 根据ID删除

    /**
     * 删除用户必须是管理员
     *
     * @param userId
     * @return
     */

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String userId) {
        //首先要获取token   token是从请求头获取
     /*   String header = request.getHeader("Authorization");
        //判断token是否为空
        if (header == null) {
            return new Result(false, StatusCode.LOGINERROR, "没有登录");
        }
        //判断token是否是bearer 开头 如果不是bearer开头，则说明token不正确
        if (!header.startsWith("Bearer ")) {
            return new Result(false, StatusCode.LOGINERROR, "登录错误，需要重新登录");
        }
        //获取token的值   需要把bearer加空格去掉
        String token = header.substring(7);
        //根据token获取值claims 然后验证token的正确性，同时可以获取到权限是什么
        Claims claims = jwtUtil.parseJWT(token);
        try {
            if (claims == null) {
                return new Result(false, StatusCode.ACCESSERROR, "权限不足");
            }
            //如果权限不是admin，则没有权限进行操作  返回没有权限
            if (!"admin".equals(claims.get("roles"))) {
                return new Result(false, StatusCode.ACCESSERROR, "权限不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ACCESSERROR, "权限异常");
        }*/
        /**
         * 从request中获取claims,判断是否管理员
         * 如果不是管理员，就没有权限
         */
        Claims claims = (Claims) request.getAttribute("roles_admin");
        if (claims==null){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userService.deleteById(userId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //POST /user/login 登陆
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        User loginUser = userService.login(user.getMobile(), user.getPassword());
        if (loginUser != null) {
            return new Result(true, StatusCode.OK, "登录成功");
        } else {
            return new Result(true, StatusCode.LOGINERROR, "登录失败");
        }
    }


    //注册用户
    // POST/user/register/{code}注册用户
    @RequestMapping(value = "register/{code}", method = RequestMethod.POST)
    public Result register(@PathVariable String code,
                           @RequestBody User user) {
        userService.register(user, code);

        return new Result(true, StatusCode.OK, "注册成功");

    }

    //发送短信验证码
    //POST /user/sendsms/{mobile}发送手机验证码

    @RequestMapping(value = "sendsms/{mobile}", method = RequestMethod.POST)
    public Result sendsms(@PathVariable String mobile) {
        userService.sendsms(mobile);
        return new Result(true, StatusCode.OK, "手机验证码发送成功");
    }

}
