package com.fred.controller;

import com.fred.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther fred
 * @create 2021-03-18 21:17
 */

@RequestMapping("moonker")
@Controller
public class MoonkerController {

    @Resource
    private ArticleService articleServiceImpl;

    @GetMapping
    public void readArticle(HttpServletRequest req, HttpServletResponse resp, DispatcherServlet dispatcherServlet) throws ServletException, IOException {
//        articleServiceImpl.readArticle(articleID);
        System.out.println("moonker/read/{articleID}");
        req.getRequestDispatcher("article/mb00001.html").forward(req,resp);
        System.out.println("moonker/read/{articleID}");
//        return "moonker/article/mb00001";
    }
}
