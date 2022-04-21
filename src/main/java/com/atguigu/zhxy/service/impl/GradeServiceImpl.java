package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.GradeMapper;
import com.atguigu.zhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.zhxy.pojo.Grade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:20
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            wrapper.like("name",gradeName);
        }
        wrapper.orderByDesc("id");
        Page<Grade> gradePage = baseMapper.selectPage(page, wrapper);
        return gradePage;
    }

    @Override
    public List<Grade> getGrades() {
        List<Grade> gradeList = baseMapper.selectList(null);
        return gradeList;
    }
}
