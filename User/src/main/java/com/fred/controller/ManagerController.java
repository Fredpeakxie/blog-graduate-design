package com.fred.controller;

import com.fred.entities.CommonResult;
import com.fred.entities.Manager;
import com.fred.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther fred
 * @create 2021-04-19 15:41
 */
@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Resource
    private ManagerService managerService;


    @PostMapping("/registry")
    public CommonResult<Manager> registry(@RequestBody Manager manager){
        log.info("manager registry");
        return managerService.registry(manager);
    }


}
