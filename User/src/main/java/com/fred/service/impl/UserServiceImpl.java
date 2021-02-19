package com.fred.service.impl;

import com.fred.entities.CommonResult;
import com.fred.entities.User;
import com.fred.repository.UserDao;
import com.fred.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther fred
 * @create 2021-02-19 12:42
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public CommonResult<User> registry(User user) {
        CommonResult<User> commonResult = new CommonResult<User>();
        //1检验 用户名是否存在 存在则返回 用户名已存在 请更换用户名再次注册
        //检验的第二部分 检验是否有username,password,email,nickname,这些未来放入前端校验，然后后端使用validation功能校验
        //检测邮箱是否可用给用户发相关邮件
        int usernameRepeat = userDao.checkUsernameExisting(user.getUsername());
        int emailRepeat = userDao.checkEmailExisting(user.getEmail());
        log.info("usernameRepeat: "+usernameRepeat);
        if((usernameRepeat == 0)&&(emailRepeat == 0)){
            //创建用户
            userDao.createUser(user);
            return commonResult.addCode(200).addMessage("创建用户成功").addData(user);
        }else if((usernameRepeat != 0)&&(emailRepeat == 0)){
            return commonResult.addCode(400).addMessage("用户名重复,请更换用户名后重试").addData(user);
        }else if((usernameRepeat == 0)&&(emailRepeat != 0)){
            return commonResult.addCode(400).addMessage("邮箱重复,请更换邮箱后重试").addData(user);
        }else{
            return commonResult.addCode(400).addMessage("用户名和邮箱均重复,请更换用户名和邮箱后重试").addData(user);
        }
    }

    @Override
    public CommonResult<User> login(User user) {
        return null;
    }
}
