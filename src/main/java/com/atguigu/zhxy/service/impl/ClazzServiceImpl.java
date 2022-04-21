package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.ClazzMapper;
import com.atguigu.zhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.zhxy.pojo.Clazz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:18
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, Clazz clazz) {
        QueryWrapper<Clazz> wrapper = new QueryWrapper<>();
        if(clazz!=null){
            if (!StringUtils.isEmpty(clazz.getName())) {
                wrapper.like("name",clazz);
            }
            if(!StringUtils.isEmpty(clazz.getGradeName())){
                wrapper.like("grade_name",clazz.getGradeName());
            }
        }
        wrapper.orderByDesc("id");
        Page<Clazz> page = baseMapper.selectPage(clazzPage, wrapper);
        return page;
    }

    @Override
    public List<Clazz> getClazzs() {
        List<Clazz> clazzList = baseMapper.selectList(null);
        return clazzList;
    }
}
