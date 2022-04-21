package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
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
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            Student student
    ){
        Page<Student> studentPage = new Page<>(pageNo, pageSize);
        IPage<Student> page=studentService.getStudentByOpr(studentPage,student);
        return Result.ok(page);
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        student.setPassword(MD5.encrypt(student.getPassword()));
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> studentList){
        studentService.removeByIds(studentList);
        return Result.ok();
    }
}
