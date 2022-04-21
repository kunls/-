package com.atguigu.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.zhxy.pojo.Clazz;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:13
 */
public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, Clazz clazz);

    List<Clazz> getClazzs();
}
