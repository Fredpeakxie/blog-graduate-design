package com.fred.test;

import com.alibaba.fastjson.JSON;
import com.fred.config.ESConfig;
import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.entities.mapper.ArticleDetailES;
import com.fred.service.ArticleService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-28 11:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleInsertES {
    
    @Resource
    private RestHighLevelClient client;

    @Resource
    ArticleService articleService;


    @Test
    public void index() throws IOException {
        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(0L, 14L).getData();
        for (ArticleDetail ad : articleDetailList) {
            IndexRequest request = new IndexRequest("moonker","article");
            request.id(ad.getArticleId().toString());
            String adJString = JSON.toJSONString(ad);
            request.source(adJString,XContentType.JSON);
        }
//        String string = JSON.toJSONString(user);
//        request.source(string, XContentType.JSON);
//        IndexResponse index = client.index(request, ESConfig.COMMON_OPTIONS);
//        System.out.println(index);
    }

    @Test
    public void bulk() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(0L, 14L).getData();
        for (ArticleDetail ad : articleDetailList) {
            IndexRequest indexRequest = new IndexRequest("moonker","article");
            indexRequest.id(ad.getArticleId().toString());
            String adJString = JSON.toJSONString(ad);
            indexRequest.source(adJString,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest,ESConfig.COMMON_OPTIONS);
        System.out.println("bulk:"+bulkRequest.toString());
    }

    @Test
    public void bulkSug() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(0L, 20L).getData();
        for (ArticleDetail ad : articleDetailList) {
            IndexRequest indexRequest = new IndexRequest("moonker","article");
            indexRequest.id(ad.getArticleId().toString());
            ArticleDetailES articleDetailES = new ArticleDetailES(ad);
            String adJString = JSON.toJSONString(articleDetailES);
            indexRequest.source(adJString,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest,ESConfig.COMMON_OPTIONS);
        System.out.println("bulk:"+bulkRequest.toString());
    }

    @Test
    public void titleBulk() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(0L, 30L).getData();
        for (ArticleDetail ad : articleDetailList) {
            IndexRequest indexRequest = new IndexRequest("title1","completion");
            indexRequest.id(ad.getArticleId().toString());
            String adJString = "{\"title\":\""+ad.getTitle()+"\"}";
            System.out.println(adJString);
            indexRequest.source(adJString,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest,ESConfig.COMMON_OPTIONS);
        System.out.println("bulk:"+bulkRequest.toString());
    }

    @Test
    public void test(){
        List<ArticleDetail> articleDetailList = articleService.getArticleDetailList(0L, 14L).getData();
        System.out.println(JSON.toJSONString(articleDetailList.get(0)));
    }

//    @Test
//    public void insertTitle(){
//        IndexRequest indexRequest = new IndexRequest("title1","completion");
//        indexRequest.id(ad.getArticleId().toString());
//        String adJString = "{\"title\":\""+ad.getTitle()+"\"}";
//        System.out.println(adJString);
//        indexRequest.source(adJString,XContentType.JSON);
//    }
}
