package com.fred.service;

import com.fred.entities.CommonResult;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-20 16:53
 */
public interface UARService {
    List<Long> getUserLike(Long userId);

    List<Long> getUserMark(Long userId);

    CommonResult<String> userLikeAdd(Long userId, Long articleId);

    CommonResult<String> userLikeRemove(Long userId, Long articleId);

    CommonResult<String> userMarkAdd(Long userId, Long articleId);

    CommonResult<String> userMarkRemove(Long userId, Long articleId);
}
