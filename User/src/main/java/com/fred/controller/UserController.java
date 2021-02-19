package com.fred.controller;

import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.repository.UserDao;
import com.fred.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @auther fred
 * @create 2021-02-19 11:23
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private IUserService userServiceImpl;


    @PostMapping("/registry")
    public CommonResult<User> registry(@RequestBody User user){
        log.info("registry");
        return userServiceImpl.registry(user);
    }

}
