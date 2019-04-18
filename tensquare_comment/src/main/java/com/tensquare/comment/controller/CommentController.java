package com.tensquare.comment.controller;


import com.tensquare.comment.pojo.Comment;
import com.tensquare.comment.service.CommentService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    @RequestMapping(value = "/article/{articleid}/{page}/{size}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleid,
                           @PathVariable Integer page,
                           @PathVariable Integer size){
        Page<Comment> pageData = commentService.findByPage(articleid, page, size);
        PageResult<Comment> pageResult = new PageResult<>();
        pageResult.setTotal(pageData.getTotalElements());
        pageResult.setRows(pageData.getContent());
        return new Result(true,StatusCode.OK,"查找成功",pageResult);
    }

    @RequestMapping(value = "{commnetId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Comment> commentList = commentService.findAll();
        return new Result(true,StatusCode.OK,"查询所有成功",commentList);
    }
}
