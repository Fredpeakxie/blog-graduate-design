package com.fred.service.impl;

import com.fred.entities.CommonResult;
import com.fred.entities.Manager;
import com.fred.entities.RetCode;
import com.fred.repository.ManagerDao;
import com.fred.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther fred
 * @create 2021-04-19 15:44
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    ManagerDao managerDao;

    @Override
    public CommonResult<Manager> backOfficeRegistry(Manager manager) {
        CommonResult<Manager> managerCommonResult;
        if(managerDao.countManager(manager) != 0){
            managerCommonResult = new CommonResult<Manager>().addData(manager).addCode(RetCode.MANAGER_REGISTRY_FAIL).addMessage("重复的用户名或邮箱");
        }else {
            managerDao.createManager(manager);
            managerCommonResult = new CommonResult<Manager>().addData(manager).addCode(RetCode.OK).addMessage("添加版务成功");
        }
        return managerCommonResult;
    }

    @Override
    public Boolean login(String username,String password) {
        if(username.equals("fred")&&password.equals("QweAsdZxc0831"))
            return true;
        else
            return false;
    }

    @Override
    public List<Manager> getManagers() {
        return managerDao.getManagers();
    }

    @Override
    public void deleteManager(Integer managerId) {
        managerDao.deleteManager(managerId);
    }

}
