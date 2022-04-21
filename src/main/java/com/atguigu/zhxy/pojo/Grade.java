package com.atguigu.zhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lxkstart
 * @date 2022/4/14 - 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_grade")
public class Grade {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String name;
    private String manager;
    private String email;
    private String telephone;
    private String introducation;
}
