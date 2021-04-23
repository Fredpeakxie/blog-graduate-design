package com.fred.service.impl;

import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.repository.ManagerDao;
import com.fred.service.BackOfficeService;
import com.fred.tools.JsonTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-04-22 9:20
 */
@Service
public class BackOfficeServiceImpl implements BackOfficeService {


    @Resource
    private RestTemplate restTemplate;

    @Resource
    ManagerDao managerDao;

    @Value("${path.ipAddress}")
    public String IP_ADDRESS;

    @Value("${path.article}")
    public String ARTICLE_URL;

    @Override
    public List<ArticleDetail> getArticleList() {
        String url = ARTICLE_URL + "/ArticleDetail/"+0+"/"+100;
        CommonResult<List<ArticleDetail>> adListCR = getADListCommonResult(url);
        return adListCR.getData();
    }

    @Override
    public List<ArticleDetail> getArticleList(String query) {
        String url = ARTICLE_URL + "/search/"+0+"/"+100+"/"+query;
        CommonResult<List<ArticleDetail>> adListCR = getADListCommonResult(url);
        return adListCR.getData();
    }

    @Override
    public List<ArticleDetail> getArticleList(Integer userId) {
        String url = ARTICLE_URL + "/ArticleDetail/my/"+0+"/"+100+"/"+userId;
        CommonResult<List<ArticleDetail>> adListCR = getADListCommonResult(url);
        return adListCR.getData();
    }

    @Override
    public void deleteArticle(Integer id) {
        restTemplate.delete(ARTICLE_URL + "/manager/"+id);
    }

    private CommonResult<List<ArticleDetail>> getADListCommonResult(String url) {
        String adListCRJson = restTemplate.getForObject(url, String.class);
        CommonResult<List<ArticleDetail>> adListCR = JsonTools.toADListCommonResult(adListCRJson);
        adListCR.getData().forEach((articleDetail)->{
            articleDetail.setHref("http://"+IP_ADDRESS+":9001/moonker/article/mb"+String.format("%05d",articleDetail.getArticleId())+".html");
        });
        return adListCR;
    }

    @Override
    public Integer backOfficeLogin(String username, String password) {
        return managerDao.login(username,password);
    }
}
