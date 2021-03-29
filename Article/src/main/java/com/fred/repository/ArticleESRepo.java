package com.fred.repository;

import com.alibaba.fastjson.JSON;
import com.fred.config.ESConfig;
import com.fred.entities.ArticleDetail;
import com.fred.entities.mapper.ArticleDetailES;
import lombok.extern.slf4j.Slf4j;
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
        IndexRequest indexRequest = new IndexRequest(INDEX,TYPE);
        indexRequest.id(ad.getArticleId().toString());
        ArticleDetailES articleDetailES = new ArticleDetailES(ad);
        String adJString = JSON.toJSONString(articleDetailES);
        indexRequest.source(adJString, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, ESConfig.COMMON_OPTIONS);
        log.info(index.toString());
    }

    public List<ArticleDetail> searchArticle(int from,int size,String queryText) throws IOException {
        //1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices(INDEX);
        //指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        1.1 检索条件 score,markNum,likeNum,articleId 对排名的影响
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(queryText,"title","nickname"))
                .from(from).size(size)
                .sort("markNum",SortOrder.DESC)
                .sort("likeNum",SortOrder.DESC)
                .sort("readNum",SortOrder.DESC)
                .sort("articleId",SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        //2. 执行检索
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);

        //3.结果分析
        JSON.parseObject(search.toString(), Map.class);
        //3.1 获取所有查到的数据 对应 "hits":
        SearchHits hits = search.getHits();
        SearchHit[] eachHits = hits.getHits();
        //3.2 转换为对象
        List<ArticleDetail> articleDetails = new ArrayList<>();
        for (SearchHit hit: eachHits) {
            String jsonString = hit.getSourceAsString();
            ArticleDetail articleDetail = JSON.parseObject(jsonString, ArticleDetail.class);
            articleDetails.add(articleDetail);
        }
        return articleDetails;
    }

}
