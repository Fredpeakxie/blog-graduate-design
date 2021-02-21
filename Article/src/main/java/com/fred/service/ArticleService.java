package com.fred.service;

import com.fred.entities.Article;
import com.fred.entities.CommonResult;

/**
 * @auther fred
 * @create 2021-02-20 9:23
 */
public interface ArticleService {

    CommonResult<Long> publishArticle(Article article);

}
