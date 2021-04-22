package com.fred.service.impl;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.repository.*;
import com.fred.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


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

    @Resource
    private LikeDao likeDao;

    @Resource
    private MarkDao markDao;

    @Transactional
    @Override
    public CommonResult<Long> publishArticle(Article article) {
        CommonResult<Long> commonResult = new CommonResult<>();
        upsertArticle(article);
        log.info("save:articleId "+article.getArticleId()+"authorId: "+article.getAuthorId());
        commonResult.addData(article.getArticleId());
        commonResult = articleRepo.saveArticle(article,commonResult);
        ArticleDetail articleDetail = articleDetailDao.getArticleDetail(article.getArticleId());
        try {
            articleESRepo.addArticle(articleDetail);
        } catch (IOException e) {
            //TODO throw exception and GlobalExceptionHandler
            log.warn("articleESRepoSaveFail: "+article.getArticleId());
        }
        return commonResult;
    }

    private void upsertArticle(Article article) {
        if (article.getArticleId() != null)
            articleDao.updateArticle(article);
        else
            articleDao.insertArticle(article);
    }

//    @Transactional
//    @Override
//    public CommonResult<Long> updateArticle(Article article) {
//        CommonResult<Long> commonResult = new CommonResult<>();
//        articleDao.updateArticle(article);
//        log.info("update:articleId "+article.getArticleId()+"authorId: "+article.getAuthorId());
//        commonResult.addData(article.getArticleId());
//        commonResult = articleRepo.saveArticle(article,commonResult);
//        ArticleDetail articleDetail = articleDetailDao.getArticleDetail(article.getArticleId());
//        try {
//            articleESRepo.addArticle(articleDetail);
//        } catch (IOException e) {
//            //TODO throw exception and GlobalExceptionHandler
//            log.warn("articleESRepoSaveFail: "+article.getArticleId());
//        }
//        return null;
//    }

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
            e.printStackTrace();
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
    public CommonResult<String> saveArticlesToELK(Integer num) {
        List<ArticleDetail> articleDetailList = getArticleDetailList(0L, num.longValue()).getData();
        try {
            articleESRepo.saveArticles(articleDetailList);
            return new CommonResult<String>(RetCode.OK,"saveArticlesToELK succeed","ok");
        } catch (IOException e) {
            log.warn("article save to ELK fail");
            return new CommonResult<String>(RetCode.OK,"saveArticlesToELK succeed","ok");
        }
    }

    @Transactional
    @Override
    public void deleteArticle(Integer articleId) {
        articleDao.deleteArticle(articleId);
        likeDao.removeArticle(articleId);
        markDao.removeArticle(articleId);
        try {
            articleESRepo.deleteArticle(articleId);
        } catch (IOException e) {
            System.out.println("删除失败");
        }
    }


    @Override
    public CommonResult<String> readArticle(Long articleID) {
        CommonResult<String> commonResult = new CommonResult<>();
        articleDao.readNumAdd(articleID);
        return commonResult.addCode(RetCode.OK).addMessage("readNum added").addData("ok");
    }
}
