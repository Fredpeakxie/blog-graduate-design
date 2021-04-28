package com.fred.service;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;

import java.util.List;

/**
 * @auther fred
 * @create 2021-02-20 9:23
 */
public interface ArticleService {

    /**
     * #{userID},#{type},#{title},#{articleContent}
     * @param article
     * @return
     */
    CommonResult<Long> publishArticle(Article article);

    /**
     *
     * @param start 开始数字
     * @param num   总共需求数字
     * @return
     */
    CommonResult<List<Article>> getArticle(Long start,Long num);

    CommonResult<List<ArticleDetail>> getArticleDetailList(Long start, Long num);

    /**
     * 当用户读取文章时怎加文章阅读数
     * 未来可能将图片加载加到该方法当中
     * @param articleID
     * @return
     */
    CommonResult<String> readArticle(Long articleID);

    CommonResult<List<ArticleDetail>> getMarkedArticleDetailList(Long start, Long num, Long userId);

    CommonResult<List<ArticleDetail>> searchArticle(Integer from, Integer size, String queryText);

    CommonResult<List<String>> suggest(String queryText);

    CommonResult<List<ArticleDetail>> getMyArticleDetailList(Long start, Long num, Long authorId);

    CommonResult<String> saveArticlesToELK(Integer num);

    CommonResult<Boolean> deleteArticle(Integer articleId);
//
//    CommonResult<Long> updateArticle(Article article);
}
