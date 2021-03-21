package com.fred.service.impl;

import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.entities.User;
import com.fred.entities.UserDetail;
import com.fred.repository.UserDao;
import com.fred.repository.UserDetailDao;
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

    @Resource
    private UserDetailDao userDetailDao;

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
            log.info("创建用户成功");
            return commonResult.addCode(RetCode.OK).addMessage("创建用户成功").addData(user);
        }else if((usernameRepeat != 0)&&(emailRepeat == 0)){
            log.info("用户名重复");
            return commonResult.addCode(RetCode.REPETITIVE_USERNAME).addMessage("用户名重复,请更换用户名后重试").addData(user);
        }else if((usernameRepeat == 0)&&(emailRepeat != 0)){
            log.info("邮箱重复");
            return commonResult.addCode(RetCode.REPETITIVE_EMAIL).addMessage("邮箱重复,请更换邮箱后重试").addData(user);
        }else{
            log.info("用户名和邮箱均重复");
            return commonResult.addCode(RetCode.REPETITIVE_USERNAME_EMAIL).addMessage("用户名和邮箱均重复,请更换用户名和邮箱后重试").addData(user);
        }
    }

    @Override
    public CommonResult<User> login(User user) {
        CommonResult<User> commonResult = new CommonResult<>();
        // 待办 可以使用邮箱登录
        User login = userDao.login(user);
        log.info(login.toString());
        if(login!=null){
            login.setPassword("pass");
            log.info(user.getUsername()+" 登录成功");
            commonResult.addCode(RetCode.OK).addMessage("登录成功").addData(login);
        }else {
            log.info(user.getUsername()+" 用户名或密码输入错误");
            commonResult.addCode(RetCode.WRONG_PASSWORD).addMessage("用户名或密码输入错误").addData(user);
        }
        return commonResult;
    }

    @Override
    public CommonResult<UserDetail> detail(Long userId) {
        CommonResult<UserDetail> userDetailCommonResult = new CommonResult<>();
        UserDetail userDetail = userDetailDao.findByUserId(userId);
        return userDetailCommonResult.addCode(RetCode.OK)
                .addData(userDetail)
                .addMessage("get user detail");
    }
}
