package com.fred.service.impl;

import com.fred.entities.Article;
import com.fred.entities.CommonResult;
import com.fred.repository.ArticleDao;
import com.fred.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-02-20 9:24
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Override
    public CommonResult<Long> publishArticle(Article article) {
        CommonResult<Long> commonResult = new CommonResult<>();
        articleDao.insertArticle(article);
        return commonResult.addData(article.getArticleID());
    }

    @Override
    public CommonResult<List<Article>> getArticle(Long start, Long num) {
        CommonResult<List<Article>> commonResult = new CommonResult<>();
        List<Article> articles = articleDao.selectArticleByNum(start, num);
        return commonResult.addCode(200).addMessage("成功获得20条信息").addData(articles);
    }

    @Override
    public CommonResult<String> readArticle(Long articleID) {
        CommonResult<String> commonResult = new CommonResult<>();
        articleDao.readNumAdd(articleID);
        return commonResult.addCode(200).addMessage("readNum added").addData("ok");
    }
}
