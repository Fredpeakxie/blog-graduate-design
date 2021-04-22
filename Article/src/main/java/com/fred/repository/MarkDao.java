package com.fred.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-20 16:52
 */
@Mapper
public interface MarkDao {
    List<Long> getUserMark(Long userId);

    Integer userMarkAdd(@Param("userId") Long userId, @Param("articleId") Long articleId);

    Integer userMarkRemove(@Param("userId") Long userId, @Param("articleId") Long articleId);

    void removeArticle(@Param("articleId")Integer articleId);
}
