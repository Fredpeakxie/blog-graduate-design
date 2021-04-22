package com.fred.controller;

import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.entities.UserDetail;
import com.fred.service.IUserService;
import com.fred.service.impl.UserServiceImpl;
import com.fred.tools.JsonTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * @auther fred
 * @create 2021-04-19 18:27
 */
@Controller
@RequestMapping("/backOffice")
@Slf4j
public class BackOfficeController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private IUserService userService;

    @Value("${path.ipAddress}")
    public String IP_ADDRESS;

    @Value("${path.article}")
    public String ARTICLE_URL;


    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map,
                        HttpSession session){
        //TODO service
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            //登录成功
            session.setAttribute("loginUser",username);
            return "redirect:/backOffice/articles";
        }else {
            map.put("msg","用户名密码错误");
            return "login";
        }
    }

    @GetMapping("/articles")
    public String articleList(ModelMap map){
        //TODO service
        String adListCRJson = restTemplate.getForObject(ARTICLE_URL + "/ArticleDetail/"+0+"/"+100, String.class);
        CommonResult<List<ArticleDetail>> adListCR = JsonTools.toADListCommonResult(adListCRJson);
        adListCR.getData().forEach((articleDetail)->{
            articleDetail.setHref("http://"+IP_ADDRESS+":9001/moonker/article/mb"+String.format("%05d",articleDetail.getArticleId())+".html");
        });

        map.addAttribute("articles",adListCR.getData());
        return "article/list";
    }

    @GetMapping("/articles/search")
    public String articleList(@RequestParam String query,ModelMap map){
        //TODO service
        String adListCRJson = restTemplate.getForObject(ARTICLE_URL + "/search/"+0+"/"+100+"/"+query, String.class);
        CommonResult<List<ArticleDetail>> adListCR = JsonTools.toADListCommonResult(adListCRJson);
        adListCR.getData().forEach((articleDetail)->{
            articleDetail.setHref("http://"+IP_ADDRESS+":9001/moonker/article/mb"+String.format("%05d",articleDetail.getArticleId())+".html");
        });
        map.addAttribute("articles",adListCR.getData());
        return "article/list";
    }

    @GetMapping("/articles/{userId}")
    public String articleList(@PathVariable Integer userId, ModelMap map){
        //TODO service
        String adListCRJson = restTemplate.getForObject(ARTICLE_URL + "/ArticleDetail/my/"+0+"/"+100+"/"+userId, String.class);
        CommonResult<List<ArticleDetail>> adListCR = JsonTools.toADListCommonResult(adListCRJson);
        adListCR.getData().forEach((articleDetail)->{
            articleDetail.setHref("http://"+IP_ADDRESS+":9001/moonker/article/mb"+String.format("%05d",articleDetail.getArticleId())+".html");
        });

        map.addAttribute("articles",adListCR.getData());
        return "article/list";
    }

    @DeleteMapping("/article/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        restTemplate.delete(ARTICLE_URL + "/manager/"+id);
        return "redirect:/backOffice/articles";
    }

    @GetMapping("/users")
    public String userList(ModelMap map){
        List<UserDetail> udList = userService.getUserList();
        map.addAttribute("users",udList);
        return "user/list";
    }



}
