package com.fred.service;

import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.service.impl.ArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-29 19:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Resource
    ArticleService articleService;

    @Test
    public void searchTest(){
        CommonResult<List<ArticleDetail>> commonResult = articleService.searchArticle(0, 10, "fred");
        System.out.println(commonResult);
        commonResult.getData().forEach(System.out::println);
    }

    @Test
    public void suggestTest(){
        CommonResult<List<ArticleDetail>> commonResult = articleService.searchArticle(0, 10, "fred");
        System.out.println(commonResult);
        commonResult.getData().forEach(System.out::println);
    }
}
