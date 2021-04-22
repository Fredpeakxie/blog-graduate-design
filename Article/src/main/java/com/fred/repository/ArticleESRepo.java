package com.fred.repository;

import com.alibaba.fastjson.JSON;
import com.fred.config.ESConfig;
import com.fred.entities.ArticleDetail;
import com.fred.entities.mapper.ArticleDetailES;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
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
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther fred
 * @create 2021-03-28 12:25
 */
@Repository
@Slf4j
public class ArticleESRepo {

    @Resource
    private RestHighLevelClient client;

    public static final String INDEX = "moonker";
    public static final String TYPE = "article";

    public void addArticle(ArticleDetail ad) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX);
        indexRequest.id(ad.getArticleId().toString());
        ArticleDetailES articleDetailES = new ArticleDetailES(ad);
        String adJString = JSON.toJSONString(articleDetailES);
        indexRequest.source(adJString, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, ESConfig.COMMON_OPTIONS);
        log.info(index.toString());
    }

    public List<ArticleDetail> searchArticle(int from,int size,String queryText) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //TODO 检索条件 score,markNum,likeNum,articleId 对排名的影响
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(queryText,"title","nickname"))
                .from(from).size(size)
                .sort("markNum",SortOrder.DESC)
                .sort("likeNum",SortOrder.DESC)
                .sort("readNum",SortOrder.DESC)
                .sort("articleId",SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);
        SearchHits hits = search.getHits();
        SearchHit[] eachHits = hits.getHits();
        List<ArticleDetail> articleDetails = new ArrayList<>();
        for (SearchHit hit: eachHits) {
            String jsonString = hit.getSourceAsString();
            ArticleDetail articleDetail = JSON.parseObject(jsonString, ArticleDetail.class);
            articleDetails.add(articleDetail);
        }
        return articleDetails;
    }

    public List<String> suggestTitle(String queryText) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("titleSuggest",
                SuggestBuilders.completionSuggestion("sugTitle").prefix(queryText));
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);

        List<String> strings = new ArrayList<>();
        Suggest suggest = search.getSuggest();
        suggest.getSuggestion("titleSuggest").getEntries().forEach(
                queryResult -> {
                    List<? extends Suggest.Suggestion.Entry.Option> options = queryResult.getOptions();
                    for (int i = 0; i < 5 && i < options.size(); i++) {
                        strings.add(options.get(i).getText().toString());
                    }
                }
        );
        return strings;
    }


    public void saveArticles(List<ArticleDetail> articleDetailList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (ArticleDetail ad : articleDetailList) {
            IndexRequest indexRequest = new IndexRequest(INDEX);
            indexRequest.id(ad.getArticleId().toString());
            ArticleDetailES articleDetailES = new ArticleDetailES(ad);
            String adJString = JSON.toJSONString(articleDetailES);
            indexRequest.source(adJString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest,ESConfig.COMMON_OPTIONS);
        System.out.println("bulk:"+bulkRequest.toString());
    }

    public void deleteArticle(Integer articleId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX);
        deleteRequest.id(articleId.toString());
        DeleteResponse delete = client.delete(deleteRequest, ESConfig.COMMON_OPTIONS);
        log.warn(delete.toString());
    }
}
