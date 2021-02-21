package com.fred.repository;

import com.fred.entities.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther fred
 * @create 2021-02-20 9:22
 */
@Mapper
public interface ArticleDao {

    int insertArticle(Article article);

    List<Article> selectArticleByNum(@Param("start") Long start,@Param("num") Long num);

    int readNumAdd(Long articleID);
}
