package com.fred.controller;

import com.fred.entities.Article;
import com.fred.entities.CommonResult;
import com.fred.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther fred
 * @create 2021-02-20 9:23
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleServiceImpl;

    @PostMapping("/publish")
    public CommonResult<Long> publishArticle(@RequestBody Article article){
        return articleServiceImpl.publishArticle(article);
    }

}
