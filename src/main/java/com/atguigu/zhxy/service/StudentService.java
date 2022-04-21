package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.zhxy.pojo.Student;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:14
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentByToken(Long userId);

    IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student);
}
