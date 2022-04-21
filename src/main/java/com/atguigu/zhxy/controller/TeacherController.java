package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:27
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            Teacher teacher
    ){
        Page<Teacher> teacherPage = new Page<>(pageNo,pageSize);
        IPage<Teacher> page=teacherService.getTeachers(teacherPage,teacher);
        return Result.ok(page);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> teacherList){
        teacherService.removeByIds(teacherList);
        return Result.ok();
    }

}
