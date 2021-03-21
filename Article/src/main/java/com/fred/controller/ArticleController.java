package com.fred.controller;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/Article/{start}/{num}")
    public CommonResult<List<Article>> getArticleByNum(@PathVariable("start") Long start,@PathVariable("num") Long num){
        return articleServiceImpl.getArticle(start,num);
    }

    @GetMapping("/ArticleDetail/{start}/{num}")
    public CommonResult<List<ArticleDetail>> getArticleDetailByNum(@PathVariable("start") Long start, @PathVariable("num") Long num){
        return articleServiceImpl.getArticleDetailList(start,num);
    }

    @GetMapping("/ArticleDetail/{start}/{num}/{userId}")
    public CommonResult<List<ArticleDetail>> getMarkedArticleDetailByNum(@PathVariable("start") Long start, @PathVariable("num") Long num,@PathVariable("userId") Long userId){
        return articleServiceImpl.getMarkedArticleDetailList(start,num,userId);
    }


    @PostMapping("/testing")
    public CommonResult<Long> publishArticleBatch(@RequestBody Article article){
        String title = article.getTitle();
        for (int i = 0; i < 100; i++) {
            article.setTitle(title+i);
            articleServiceImpl.publishArticle(article);
        }
        return null;
    }

    @PutMapping("/readNumAdd/{articleID}")
    public String readNumAdd(@PathVariable Long articleID){
        articleServiceImpl.readArticle(articleID);
        return "";
    }


}
