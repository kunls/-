package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.GradeService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.Mysqlx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:27
 */
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> gradeList=gradeService.getGrades();
        return Result.ok(gradeList);
    }

    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){//前端json串通过@RequestBody来转化成参数接受
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable Integer pageNo,
                                @PathVariable Integer pageSize,
                                String gradeName){
        //分页，带条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        //通过服务层查询
        IPage<Grade> pageRs =gradeService.getGradeByOpr(page,gradeName);
        //封装Result对象并返回
        return Result.ok(pageRs);
    }
}
