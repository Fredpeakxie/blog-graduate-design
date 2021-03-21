package com.fred.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther fred
 * @create 2021-03-20 16:52
 */
@Mapper
public interface LikeDao {
    List<Long> getUserLike(@Param("userId")Long userId);

    Integer userLikeAdd(@Param("userId") Long userId,@Param("articleId") Long articleId);

    Integer userLikeRemove(@Param("userId") Long userId,@Param("articleId")  Long articleId);
}
