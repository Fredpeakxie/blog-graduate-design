package com.fred.repo;

import com.fred.entities.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-30 11:21
 */
@Mapper
public interface CommentRepo {
    Integer saveComment(Comment comment);

    List<Comment> getCommentByArticleId(Long articleId);
}