package com.fred.service.impl;

import com.fred.entities.Article;
import com.fred.entities.CommonResult;
import com.fred.repository.ArticleDao;
import com.fred.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
