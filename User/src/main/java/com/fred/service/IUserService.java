package com.fred.service;

import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.entities.UserDetail;
import com.fred.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther fred
 * @create 2021-02-19 12:33
 */
public interface IUserService {

    CommonResult<User> registry(User user);

    CommonResult<User> login(User user);

    CommonResult<UserDetail> detail(Long userId);
}
