package com.fred.service;


import com.fred.entities.CommonResult;
import com.fred.entities.Manager;

/**
 * @auther fred
 * @create 2021-04-19 15:43
 */

public interface ManagerService {
    CommonResult<Manager> registry(Manager manager);
}
