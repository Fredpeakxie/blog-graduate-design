package com.fred.controller;

import com.fred.entities.ArticleDetail;
import com.fred.entities.CommonResult;
import com.fred.entities.Manager;
import com.fred.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @auther fred
 * @create 2021-04-19 15:41
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map,
                        HttpSession session){
        if(adminService.login(username,password)){
            //登录成功
            session.setAttribute("admin",username);
            return "redirect:/admin/backOffices";
        }else {
            map.put("msg","用户名密码错误");
            return "admin/login";
        }
    }

    @GetMapping("/add")
    public String toAddPage(Model model){
        //来到添加页面
        return "admin/backOffice/add";
    }


    @GetMapping("/backOffices")
    public String managerList(ModelMap map){
        List<Manager> managers = adminService.getManagers();
        map.addAttribute("managers",managers);
        return "admin/backOffice/list";
    }

    @DeleteMapping("/backOffice/{managerId}")
    public String deleteManager(@PathVariable Integer managerId){
        adminService.deleteManager(managerId);
        return "redirect:/admin/backOffices";
    }

    @ResponseBody
    @PostMapping("/registry")
    public CommonResult<Manager> registry(@RequestBody Manager manager){
        log.info("username:{},password:{}",manager.getUsername(),manager.getPassword());
        return adminService.backOfficeRegistry(manager);
    }

    @PostMapping("/backOffice")
    public String addBackOffice(Manager manager){
        adminService.backOfficeRegistry(manager);
        return "redirect:/admin/backOffices";
    }

}
