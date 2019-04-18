package com.tensquare.user.comtroller;


import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    //查询所有
    //GET /admin  管理员全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Admin> adminList =  adminService.findAll();
        return new Result(true, StatusCode.OK,"查询所有成功",adminList);
    }

    //通过ID查询一个
   // GET /admin/{adminId} 根据ID查询
    @RequestMapping(value = "{adminId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String adminId){
        Admin admin= adminService.findById(adminId).get();
        return new Result(true,StatusCode.OK,"查询成功",admin);
    }

    //删除
    //DELETE  /admin/{adminId}  根据ID删除
    @RequestMapping(value = "{adminId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String adminId){
        adminService.deleteById(adminId);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    //修改
    //PUT /admin/{adminId} 修改管理员
    @RequestMapping(value ="{adminId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String adminId ,@RequestBody Admin admin){
        adminService.update(admin,adminId);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    //添加
    //POST /admin 增加管理员
    @RequestMapping(method = RequestMethod.POST)
    public Result addAdmin(@RequestBody Admin admin){
        adminService.addAdmin(admin);
        return new Result(true,StatusCode.OK,"添加管理员成功");
    }



    //POST /admin/login 登陆
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public Result login(@RequestBody Admin admin){
        Admin loginAdmin = adminService.login(admin.getLoginname(),admin.getPassword());
        if(loginAdmin!=null){
            return new Result(true,StatusCode.OK,"登录成功");
        }else {

            return new Result(true,StatusCode.LOGINERROR,"登录失败");
        }
    }
}
