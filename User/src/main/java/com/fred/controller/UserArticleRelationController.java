package com.fred.controller;

import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.service.UARService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-20 16:28
 */
@RestController
@RequestMapping("/uar")
public class UserArticleRelationController {

    @Resource
    private UARService uarService;

    @GetMapping("/like/{userId}")
    public CommonResult<List<Long>> userLike(@PathVariable Long userId){
        List<Long> userLike = uarService.getUserLike(userId);
        return new CommonResult<List<Long>>().addData(userLike).addCode(RetCode.OK);
    }

    @GetMapping("/mark/{userId}")
    public CommonResult<List<Long>> userMark(@PathVariable Long userId){
        List<Long> userMark = uarService.getUserMark(userId);
        return new CommonResult<List<Long>>().addData(userMark).addCode(RetCode.OK);
    }

    @PostMapping("/like/{userId}/{articleId}")
    public CommonResult<String> userLikeAdd(@PathVariable("userId") Long userId,@PathVariable("articleId") Long articleId){
        return uarService.userLikeAdd(userId,articleId);
    }

    @PostMapping("/mark/{userId}/{articleId}")
    public CommonResult<String> userMarkAdd(@PathVariable("userId") Long userId,@PathVariable("articleId") Long articleId){
        return uarService.userMarkAdd(userId,articleId);
    }

    @DeleteMapping("/like/{userId}/{articleId}")
    public CommonResult<String> userLikeRemove(@PathVariable("userId") Long userId,@PathVariable("articleId") Long articleId){
        return uarService.userLikeRemove(userId,articleId);
    }

    @DeleteMapping("/mark/{userId}/{articleId}")
    public CommonResult<String> userMarkRemove(@PathVariable("userId") Long userId,@PathVariable("articleId") Long articleId){
        return uarService.userMarkRemove(userId,articleId);
    }

}
