package com.fred.controller;

import com.fred.entities.User;
import com.fred.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther fred
 * @create 2021-02-19 11:23
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/registry")
    public User registry(@RequestBody User user){
        log.info("registry");
        userDao.createUser(user);
        return user;
    }

    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        return "hello";
    }



}
