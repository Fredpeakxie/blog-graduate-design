package com.fred.service.impl;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.repository.ArticleDao;
import com.fred.repository.ArticleDetailDao;
import com.fred.repository.ArticleESRepo;
import com.fred.repository.ArticleRepo;
import com.fred.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private ArticleESRepo articleESRepo;

    @Transactional
    @Override
    public CommonResult<Long> publishArticle(Article article) {
        CommonResult<Long> commonResult = new CommonResult<>();
        articleDao.insertArticle(article);
        log.info("save:"+article.toString());
        commonResult.addData(article.getArticleId());
        commonResult = articleRepo.saveArticle(article,commonResult);
        ArticleDetail articleDetail = articleDetailDao.getArticleDetail(article.getArticleId());
        try {
            articleESRepo.addArticle(articleDetail);
        } catch (IOException e) {
            //TODO throw exception and GlobalExceptionHandler
        }
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
    public CommonResult<List<ArticleDetail>> getMyArticleDetailList(Long start, Long num, Long authorId) {
        CommonResult<List<ArticleDetail>> commonResult = new CommonResult<>();
        List<ArticleDetail> articleDetails = articleDetailDao.getMyArticleDetailList(start, num,authorId);
        return commonResult.addCode(RetCode.OK).addMessage("成功获得20条信息").addData(articleDetails);
    }

    @Override
    public CommonResult<List<ArticleDetail>> searchArticle(Integer from, Integer size, String queryText){
        try {
            List<ArticleDetail> articleDetails = articleESRepo.searchArticle(from, size, queryText);
            return new CommonResult<List<ArticleDetail>>(RetCode.OK,"search ok",articleDetails);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return new CommonResult<List<ArticleDetail>>(RetCode.SEARCH_ARTICLE_FAIL,"fail",null);
        }
    }

    @Override
    public CommonResult<List<String>> suggest(String queryText) {
        try {
            List<String> strings = articleESRepo.suggestTitle(queryText);
            return new CommonResult<List<String>>(RetCode.OK,"suggest ok",strings);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return new CommonResult<List<String>>(RetCode.SEARCH_ARTICLE_FAIL,"fail",null);
        }
    }


    @Override
    public CommonResult<String> readArticle(Long articleID) {
        CommonResult<String> commonResult = new CommonResult<>();
        articleDao.readNumAdd(articleID);
        return commonResult.addCode(RetCode.OK).addMessage("readNum added").addData("ok");
    }
}
