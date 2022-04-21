package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.StudentMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.zhxy.pojo.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:21
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("name",loginForm.getUsername());
        wrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(wrapper);
        return student;
    }

    @Override
    public Student getStudentByToken(Long userId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id",userId);
        Student student = baseMapper.selectOne(wrapper);
        return student;
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        if(student!=null){
            if(!StringUtils.isEmpty(student.getName())){
                wrapper.like("name",student.getName());
            }
            if(!StringUtils.isEmpty(student.getClazzName())){
                wrapper.like("clazz_name",student.getClazzName());
            }
        }
        wrapper.orderByDesc("id");
        Page<Student> page = baseMapper.selectPage(studentPage, wrapper);
        return page;
    }
}
