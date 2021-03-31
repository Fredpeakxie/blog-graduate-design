package com.fred.service.impl;

import com.fred.entities.Comment;
import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.repo.CommentRepo;
import com.fred.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-30 11:21
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentRepo commentRepo;

    @Override
    public CommonResult<String> publishComment(Comment comment) {
        if(commentRepo.saveComment(comment) != 1){
            return new CommonResult<String>(RetCode.COMMENT_PUBLISH_FAIL,"comment publish failed","no");
        }else {
            return new CommonResult<String>(RetCode.OK,"comment publish succeed","no");
        }
    }

    @Override
    public CommonResult<List<Comment>> getComments(Long articleId) {
        List<Comment> commentByArticleId = commentRepo.getCommentByArticleId(articleId);
        return new CommonResult<List<Comment>>(RetCode.OK,"comment got",commentByArticleId);
    }
}
