package com.fred.repository;

import com.fred.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther fred
 * @create 2021-02-19 9:27
 */
@Mapper
public interface UserDao {

    int createUser(User user);

    User getUserById(@Param("id") long id);

    int checkUsernameExisting(@Param("username") String username);

    int checkEmailExisting(@Param("email") String email);

    User login(User user);
}
