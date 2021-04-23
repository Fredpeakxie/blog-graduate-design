package com.fred.service;


import com.fred.entities.CommonResult;
import com.fred.entities.Manager;

import java.util.List;

/**
 * @auther fred
 * @create 2021-04-19 15:43
 */

public interface AdminService {

    CommonResult<Manager> backOfficeRegistry(Manager manager);

    Boolean login(String username,String password);

    List<Manager> getManagers();

    void deleteManager(Integer managerId);
}
