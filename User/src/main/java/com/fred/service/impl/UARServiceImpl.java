package com.fred.service.impl;

import com.fred.entities.CommonResult;
import com.fred.entities.RetCode;
import com.fred.repository.LikeDao;
import com.fred.repository.MarkDao;
import com.fred.service.UARService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @auther fred
 * @create 2021-03-20 16:54
 */
@Service
public class UARServiceImpl implements UARService {

    @Resource
    private LikeDao likeDao;
    @Resource
    private MarkDao markDao;

    @Override
    public List<Long> getUserLike(Long userId) {
        return likeDao.getUserLike(userId);
    }

    @Override
    public List<Long> getUserMark(Long userId) {
        return markDao.getUserMark(userId);
    }

    @Override
    public CommonResult<String> userLikeAdd(Long userId, Long articleId) {
        CommonResult<String> commonResult = new CommonResult<String>().addData(" ");
        try {
            likeDao.userLikeAdd(userId,articleId);
        } catch (Exception e) {
            return commonResult.addCode(RetCode.USER_LIKE_ADD_FAIL)
                    .addMessage("user like add fail");
        }
        return commonResult.addCode(RetCode.OK)
                    .addMessage("user like added");
    }

    @Override
    public CommonResult<String> userLikeRemove(Long userId, Long articleId) {
        CommonResult<String> commonResult = new CommonResult<String>().addData(" ");
        try {
            likeDao.userLikeRemove(userId,articleId);
        } catch (Exception e) {
            return commonResult.addCode(RetCode.USER_LIKE_REMOVE_FAIL)
                    .addMessage("user like remove fail");
        }
        return commonResult.addCode(RetCode.OK)
                .addMessage("user like removed");
    }

    @Override
    public CommonResult<String> userMarkAdd(Long userId, Long articleId) {
        CommonResult<String> commonResult = new CommonResult<String>().addData(" ");
        try {
            markDao.userMarkAdd(userId,articleId);
        } catch (Exception e) {
            return commonResult.addCode(RetCode.USER_MARK_ADD_FAIL)
                    .addMessage("user mark add fail");
        }
        return commonResult.addCode(RetCode.OK)
                .addMessage("user mark added");
    }

    @Override
    public CommonResult<String> userMarkRemove(Long userId, Long articleId) {
        CommonResult<String> commonResult = new CommonResult<String>().addData(" ");
        try {
            markDao.userMarkRemove(userId,articleId);
        } catch (Exception e) {
            return commonResult.addCode(RetCode.USER_MARK_REMOVE_FAIL)
                    .addMessage("user mark remove fail");
        }
        return commonResult.addCode(RetCode.OK)
                .addMessage("user mark removed");
    }
}
