package com.fred;

import com.alibaba.fastjson.JSON;
import com.fred.config.ESConfig;
import com.fred.entities.ArticleDetail;
import com.fred.entities.User;
import org.assertj.core.data.Index;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.routing.allocation.allocator.BalancedShardsAllocator;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @auther fred
 * @create 2021-03-25 19:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESBasicTest {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void contextLoads(){
        System.out.println(client);
    }

    /**
     * 保存更新二合一
     * @throws IOException
     */
    @Test
    public void index() throws IOException {
        IndexRequest request = new IndexRequest("db01","users");
        request.id("1");
        User user = new User(5L, "fred02", "fred01", "347667675@qq.com", "fred01", "im fred");
        String string = JSON.toJSONString(user);
        request.source(string,XContentType.JSON);
        IndexResponse index = client.index(request, ESConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    public void searchRequest() throws IOException {
        //1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("bank");
        //指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //1.1 检索条件
//        searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        //聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        AvgAggregationBuilder ageBalanceAvg = AggregationBuilders.avg("ageBalanceAvg").field("balance");
        ageAgg.subAggregation(ageBalanceAvg);
        AvgAggregationBuilder balanceAgg = AggregationBuilders.avg("balanceAgg").field("balance");
        searchSourceBuilder.aggregation(ageAgg);
        searchSourceBuilder.aggregation(balanceAgg);

        System.out.println("searchCondition: "+searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        //2. 执行检索
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);

        System.out.println("searchResult"+search.toString());

        //3.结果分析
        JSON.parseObject(search.toString(), Map.class);
        //3.1 获取所有查到的数据 对应 "hits":
        SearchHits hits = search.getHits();
        SearchHit[] eachHits = hits.getHits();
        //3.2 转换为对象
        for (SearchHit hit: eachHits) {
//            hit.getIndex();hit.getType();hit.getId();
            String jsonString = hit.getSourceAsString();
            System.out.println(jsonString);
            Account account = JSON.parseObject(jsonString, Account.class);
            System.out.println("account:"+account);
        }

        //3.3 aggregations  获取分析数据
        Aggregations aggregations = search.getAggregations();
//        for (Aggregation aggregation : aggregations.asList()) {
//            System.out.println("aggregationName:" + aggregation.getName());
//        }
        Terms ageAggTerm = aggregations.get("ageAgg");
        ageAggTerm.getBuckets().forEach(bucket -> {
            System.out.println("age"+bucket.getKeyAsString()+"==> docCount"+bucket.getDocCount());
            Avg ageBalanceAvgResult = bucket.getAggregations().get("ageBalanceAvg");
            System.out.println("balanceAvg:" + ageBalanceAvgResult.getValue());
        });

        Avg balanceAggBalance = aggregations.get("balanceAgg");
        System.out.println("avgBalance:"+balanceAggBalance.getValue());
//        aggregations.get("a");
    }

    @Test
    public void articleSearchRequest() throws IOException {
        //1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引
        searchRequest.indices("moonker");
        //指定DSL，检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        1.1 检索条件
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring","title","nickname")).from(0).size(10);
        System.out.println("searchCondition: "+searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        //2. 执行检索
        SearchResponse search = client.search(searchRequest, ESConfig.COMMON_OPTIONS);
        System.out.println("searchResult"+search.toString());

        //3.结果分析
        JSON.parseObject(search.toString(), Map.class);
        //3.1 获取所有查到的数据 对应 "hits":
        SearchHits hits = search.getHits();
        SearchHit[] eachHits = hits.getHits();
        //3.2 转换为对象
        for (SearchHit hit: eachHits) {
//            hit.getIndex();hit.getType();hit.getId();
            String jsonString = hit.getSourceAsString();
            System.out.println(jsonString);
            ArticleDetail articleDetail = JSON.parseObject(jsonString, ArticleDetail.class);
            System.out.println("articleDetail:"+articleDetail);
        }

    }

    /**
     * Auto-generated: 2021-03-27 21:39:21
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    static class Account {

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
        public void setAccount_number(int account_number) {
            this.account_number = account_number;
        }
        public int getAccount_number() {
            return account_number;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
        public int getBalance() {
            return balance;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
        public String getFirstname() {
            return firstname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
        public String getLastname() {
            return lastname;
        }

        public void setAge(int age) {
            this.age = age;
        }
        public int getAge() {
            return age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getGender() {
            return gender;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }
        public String getEmployer() {
            return employer;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }

        public void setCity(String city) {
            this.city = city;
        }
        public String getCity() {
            return city;
        }

        public void setState(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "account_number=" + account_number +
                    ", balance=" + balance +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    ", address='" + address + '\'' +
                    ", employer='" + employer + '\'' +
                    ", email='" + email + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

}
