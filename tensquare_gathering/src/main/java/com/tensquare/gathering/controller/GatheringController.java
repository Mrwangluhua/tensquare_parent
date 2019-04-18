package com.tensquare.gathering.controller;

import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;


    /*
            POST
            /gathering
            增加活动
    */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Gathering gathering){
        gatheringService.add(gathering);
        return new Result(true,StatusCode.OK,"新增成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findALl(){
        List<Gathering> gatheringList = gatheringService.findAll();
        return new Result(true, StatusCode.OK,"查询全部列表成功",gatheringList);
    }

    /*  GET
        /gathering/{gatheringId}
        根据ID查询活动
    */
    @RequestMapping(value = "{gatheringId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String gatheringId){
        Gathering gathering = gatheringService.findById(gatheringId);
        return new Result(true,StatusCode.OK,"查询成功",gathering);
    }

    /*
        PUT
        /gathering/{gatheringId}
        修改活动
    */

    @RequestMapping(value = "{gatheringId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String gatheringId , Gathering gathering){
        gathering.setId(gatheringId);
        gatheringService.update(gathering);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /*
        DELETE
        /gathering/{gatheringId}
        根据ID删除活动
    */
    @RequestMapping(value = "{gatheringId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String gatheringId){
        gatheringService.delete(gatheringId);
        return new Result(true,StatusCode.OK,"删除成功");
    }


}
