package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:13
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminByToken(Long userId);

    IPage<Admin> getAllAdmin(Page<Admin> adminPage, String name);
}
