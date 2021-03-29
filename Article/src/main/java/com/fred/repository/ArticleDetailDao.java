package com.fred.repository;

import com.fred.entities.Article;
import com.fred.entities.ArticleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-16 20:30
 */
@Mapper
public interface ArticleDetailDao {

    List<ArticleDetail> getArticleDetailList(@Param("start") Long start, @Param("num") Long num);

    List<ArticleDetail> getMarkedArticleDetailList(@Param("start") Long start, @Param("num") Long num, @Param("userId") Long userId);

    ArticleDetail getArticleDetail(Long articleId);
}
