package com.fred.repository;

import com.fred.entities.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther fred
 * @create 2021-03-21 12:42
 */
@Mapper
public interface UserDetailDao {
    UserDetail findByUserId(Long userId);
}
