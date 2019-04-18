package com.tensquare.search.comtroller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /*
    *  新增文章
    * */
    @RequestMapping(value = "article",method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.save(article);
        return new Result(true, StatusCode.OK,"保存成功");
    }

    /*
    * 根据文章标题或者文章内容查询
    * get请求  article/{keywords}/{page}/{size}
    * */
    @RequestMapping(value = "article/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByPage(@PathVariable String   keywords,
                                 @PathVariable Integer page,
                                 @PathVariable Integer size){
        Page<Article> pageData =  articleSearchService.findByPage(keywords,page,size);
        PageResult<Article> pageResult = new PageResult<>(pageData.getTotalElements(),pageData.getContent());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

}
