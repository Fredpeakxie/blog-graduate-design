package com.fred.controller;

import com.fred.entities.Comment;
import com.fred.entities.CommonResult;
import com.fred.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-30 11:18
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    CommentService commentService;

    @PostMapping
    public CommonResult<String> publishComment(@RequestBody Comment comment){
        return commentService.publishComment(comment);
    }

    @GetMapping("/{articleId}")
    public CommonResult<List<Comment>> getComments(@PathVariable Long articleId){
        return commentService.getComments(articleId);
    }


}
