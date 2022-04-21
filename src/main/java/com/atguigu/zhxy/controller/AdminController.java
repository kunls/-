package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:26
 */
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            String name
    ){
        Page<Admin> adminPage = new Page<>(pageNo,pageSize);
        IPage<Admin> page=adminService.getAllAdmin(adminPage,name);
        return Result.ok(page);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> adminList){
        adminService.removeByIds(adminList);
        return Result.ok();
    }
}
