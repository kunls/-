package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.TeacherMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.zhxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:25
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("name",loginForm.getUsername());
        wrapper.eq("password",MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(wrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherByToken(Long userId) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("id",userId);
        Teacher teacher = baseMapper.selectOne(wrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getTeachers(Page<Teacher> teacherPage, Teacher teacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if(teacher!=null) {
            if (!StringUtils.isEmpty(teacher.getName())) {
                wrapper.like("name", teacher.getName());
            }
            if(!StringUtils.isEmpty(teacher.getClazzName())){
                wrapper.like("clazz_name",teacher.getClazzName());
            }
        }
        wrapper.orderByDesc("id");
        Page<Teacher> page = baseMapper.selectPage(teacherPage, wrapper);
        return page;
    }
}
