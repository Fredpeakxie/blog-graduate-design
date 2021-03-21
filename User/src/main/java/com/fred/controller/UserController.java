package com.fred.controller;

import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.entities.UserDetail;
import com.fred.repository.UserDao;
import com.fred.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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

    @GetMapping("/test")
    public String test(){
        log.info("test:get");
        return "test method:get";
    }

    @PostMapping("/test")
    public User testPost(){
        log.info("test:post");
        User user = new User();
        user.setUserID(100L);
        user.setNickname("fred");
        return user;
    }


    @PostMapping("/registry")
    public CommonResult<User> registry(@RequestBody User user){
        log.info("registry");
        return userServiceImpl.registry(user);
    }

    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody User user){
        log.info("login");
        return userServiceImpl.login(user);
    }

    @GetMapping("/detail/{userId}")
    public CommonResult<UserDetail> detail(@PathVariable Long userId){
        log.info("user detail"+userId);
        return userServiceImpl.detail(userId);
    }

    @PostMapping
    public CommonResult<User> updateUser(@RequestBody User user){
        log.info("update user");
        return userServiceImpl.updateUser(user);
    }

    @PostMapping("/image")
    public CommonResult<String> setPortrait(@RequestBody Map<String,Object> map){
        log.info("set portrait");
        Long userId = ((Integer)map.get("userId")).longValue();
        String bitmap = (String)map.get("bitmap");
        return userServiceImpl.setPortrait(userId,bitmap);
    }

}
