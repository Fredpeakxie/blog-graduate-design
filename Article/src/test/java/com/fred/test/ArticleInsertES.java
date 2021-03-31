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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fred.repository.ArticleESRepo.INDEX;

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
    @Test
    public void sugTitle() throws IOException {
        String query = "spring";
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("titleSuggest",
                SuggestBuilders.completionSuggestion("sugTitle").prefix(query));
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);

        Suggest suggest = search.getSuggest();
        suggest.getSuggestion("titleSuggest").getEntries().forEach(
                queryResult -> queryResult.getOptions().forEach(
                        record -> {
                            System.out.println(record.getText());
                        }
                )
        );
    }
}
