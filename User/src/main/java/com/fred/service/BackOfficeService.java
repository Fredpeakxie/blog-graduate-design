package com.fred.service;

import com.fred.entities.ArticleDetail;

import java.util.List;

/**
 * @auther fred
 * @create 2021-04-22 9:19
 */
public interface BackOfficeService {
    List<ArticleDetail> getArticleList();

    List<ArticleDetail> getArticleList(String query);

    List<ArticleDetail> getArticleList(Integer userId);

    void deleteArticle(Integer id);

    Integer backOfficeLogin(String username, String password);
}
