package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.AdminMapper;
import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:17
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",loginForm.getUsername());
        String password = MD5.encrypt(loginForm.getPassword());
        wrapper.eq("password", password);
        Admin admin = baseMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public Admin getAdminByToken(Long userId) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("id",userId);
        Admin admin = baseMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public IPage<Admin> getAllAdmin(Page<Admin> adminPage, String name) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("id");
        Page<Admin> page = baseMapper.selectPage(adminPage, wrapper);
        return page;
    }
}
