package com.fred.repository;

import com.fred.entities.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther fred
 * @create 2021-02-20 9:22
 */
@Mapper
public interface ArticleDao {

    int insertArticle(Article article);

}
