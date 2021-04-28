package com.fred.controller;

import com.fred.entities.ArticleDetail;
import com.fred.entities.Comment;
import com.fred.entities.UserDetail;
import com.fred.service.BackOfficeService;
import com.fred.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    private IUserService userService;

    @Resource
    private BackOfficeService backOfficeService;


    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map,
                        HttpSession session){
        if(backOfficeService.backOfficeLogin(username,password)>0){
            //登录成功
            session.setAttribute("loginUser",username);
            return "redirect:/backOffice/articles";
        }else {
            map.put("msg","用户名密码错误");
            return "/login";
        }
    }

    @GetMapping("/articles")
    public String articleList(ModelMap map){
        List<ArticleDetail> adList = backOfficeService.getArticleList();
        map.addAttribute("articles",adList);
        return "backOffice/article/list";
    }

    @GetMapping("/articles/search")
    public String articleList(@RequestParam String query,ModelMap map){
        List<ArticleDetail> adList = backOfficeService.getArticleList(query);
        map.addAttribute("articles",adList);
        return "backOffice/article/list";
    }

    @GetMapping("/articles/{userId}")
    public String articleList(@PathVariable Integer userId, ModelMap map){
        List<ArticleDetail> adList = backOfficeService.getArticleList(userId);
        map.addAttribute("articles",adList);
        return "backOffice/article/list";
    }

    @DeleteMapping("/article/{id}")
    public String deleteArticle(@PathVariable("id") Integer id){
        backOfficeService.deleteArticle(id);
        return "redirect:/backOffice/articles";
    }

    @GetMapping("/users")
    public String userList(ModelMap map){
        List<UserDetail> udList = userService.getUserList();
        map.addAttribute("users",udList);
        return "backOffice/user/list";
    }

    @GetMapping("/comments/userId/{userId}")
    public String CommentListByUserId(ModelMap map,@PathVariable Integer userId ){
        List<Comment> commentList = backOfficeService.getCommentListByUserId(userId);
        map.addAttribute("comments",commentList);
        return "backOffice/comment/list";
    }

    @GetMapping("/comments/articleId/{articleId}")
    public String CommentListByArticleId(ModelMap map,@PathVariable Integer articleId ){
        List<Comment> commentList = backOfficeService.getCommentListByArticleId(articleId);
        map.addAttribute("comments",commentList);
        return "backOffice/comment/list";
    }

    @GetMapping("/comments")
    public String CommentList(ModelMap map){
        List<Comment> commentList = backOfficeService.getCommentList();
        map.addAttribute("comments",commentList);
        return "backOffice/comment/list";
    }

    @DeleteMapping("/comment/{commentId}")
    public String CommentList(@PathVariable Integer commentId){
        backOfficeService.deleteComment(commentId);
        return "redirect:/backOffice/comments";
    }

}
