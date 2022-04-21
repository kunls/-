package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.service.ClazzService;
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
@RequestMapping("/sms/clazzController")
public class ClazzController {
    ///sms/clazzController/getClazzsByOpr/1/3
    @Autowired
    ClazzService clazzService;

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize,
            Clazz clazz
    ){
        Page<Clazz> clazzPage = new Page<>(pageNo, pageSize);
        IPage<Clazz> page=clazzService.getClazzsByOpr(clazzPage,clazz);
        return Result.ok(page);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> clazzList){
        clazzService.removeByIds(clazzList);
        return Result.ok();
    }

    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzList=clazzService.getClazzs();
        return Result.ok(clazzList);
    }
}
