package com.fred.service;

import com.fred.entities.Comment;
import com.fred.entities.CommonResult;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-30 11:20
 */
public interface CommentService {
    CommonResult<String> publishComment(Comment comment);

    CommonResult<List<Comment>> getComments(Long articleId);

    Boolean deleteCommentByArticleId(Integer articleId);

    CommonResult<List<Comment>> getCommentsByUserId(Long userId);
}
