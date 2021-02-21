package com.fred.controller;

import com.fred.entities.Article;
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

    @GetMapping("/getArticle/{start}/{num}")
    public CommonResult<List<Article>> getArticleByNum(@PathVariable("start") Long start,@PathVariable("num") Long num){
        return articleServiceImpl.getArticle(start,num);
    }

    @PutMapping("/read/{articleID}")
    public CommonResult<String> readNumAdd(@PathVariable Long articleID){
        return articleServiceImpl.readArticle(articleID);
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

}
