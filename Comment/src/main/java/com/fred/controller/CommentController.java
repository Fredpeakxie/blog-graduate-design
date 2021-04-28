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
    public CommonResult<List<Comment>> getCommentsByArticleId(@PathVariable Long articleId){
        return commentService.getComments(articleId);
    }

    @GetMapping("/byUserId/{userId}")
    public CommonResult<List<Comment>> getCommentsByUserId(@PathVariable Long userId){
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping
    public CommonResult<List<Comment>> getComments(){
        return commentService.getComments();
    }

    @DeleteMapping("/byArticleId/{articleId}")
    public Boolean deleteCommentByArticleId(@PathVariable Integer articleId){
        return commentService.deleteCommentByArticleId(articleId);
    }

    @DeleteMapping("/{commentId}")
    public CommonResult<Boolean> deleteCommentByCommentId(@PathVariable Integer commentId){
        return commentService.deleteComment(commentId);
    }


}
