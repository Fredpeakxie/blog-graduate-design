package com.fred.service.impl;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.repository.ArticleDao;
import com.fred.repository.ArticleDetailDao;
import com.fred.repository.ArticleRepo;
import com.fred.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.fred.repository.ArticleRepo.ARTICLE_PIC;

/**
 * @auther fred
 * @create 2021-02-20 9:24
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Resource
    private ArticleDetailDao articleDetailDao;

    @Resource
    private ArticleRepo articleRepo;

    @Override
    public CommonResult<Long> publishArticle(Article article) {
        CommonResult<Long> commonResult = new CommonResult<>();
        articleDao.insertArticle(article);
        commonResult.addData(article.getArticleID());
        commonResult = articleRepo.saveArticle(article,commonResult);
        return commonResult;
    }

    @Override
    public CommonResult<List<Article>> getArticle(Long start, Long num) {
        CommonResult<List<Article>> commonResult = new CommonResult<>();
        List<Article> articles = articleDao.selectArticleByNum(start, num);
        return commonResult.addCode(RetCode.OK).addMessage("成功获得20条信息").addData(articles);
    }

    @Override
    public CommonResult<List<ArticleDetail>> getArticleDetailList(Long start, Long num) {
        CommonResult<List<ArticleDetail>> commonResult = new CommonResult<>();
        List<ArticleDetail> articleDetails = articleDetailDao.getArticleDetailList(start, num);
        return commonResult.addCode(RetCode.OK).addMessage("成功获得20条信息").addData(articleDetails);
    }

    @Override
    public CommonResult<List<ArticleDetail>> getMarkedArticleDetailList(Long start, Long num, Long userId) {
        CommonResult<List<ArticleDetail>> commonResult = new CommonResult<>();
        List<ArticleDetail> articleDetails = articleDetailDao.getMarkedArticleDetailList(start, num,userId);
        return commonResult.addCode(RetCode.OK).addMessage("成功获得20条信息").addData(articleDetails);
    }


    @Override
    public CommonResult<String> readArticle(Long articleID) {
        CommonResult<String> commonResult = new CommonResult<>();
        articleDao.readNumAdd(articleID);
        return commonResult.addCode(RetCode.OK).addMessage("readNum added").addData("ok");
    }
}
