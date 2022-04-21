package com.atguigu.zhxy.pojo;

import lombok.Data;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:09
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
