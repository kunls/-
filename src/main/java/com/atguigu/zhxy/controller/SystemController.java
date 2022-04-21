package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author lxkstart
 * @date 2022/4/14 - 17:31
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;



    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @PathVariable String oldPwd,
            @PathVariable String newPwd,
            @RequestHeader("token") String token
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.fail().message("长时间未操作，请重新登陆");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);
        switch (userType){
            case 1:
                QueryWrapper<Admin> wrapper = new QueryWrapper<>();
                wrapper.eq("id",userId).eq("password",oldPwd);
                Admin admin = adminService.getOne(wrapper);
                if(admin!=null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }
                else{
                    return Result.fail().message("原密码输入有误!");
                }
                break;
            case 2:
                QueryWrapper<Student> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("id",userId).eq("password",oldPwd);
                Student student = studentService.getOne(wrapper2);
                if(student!=null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }
                else{
                    return Result.fail().message("原密码输入有误!");
                }
                break;
            case 3:
                QueryWrapper<Teacher> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("id",userId).eq("password",oldPwd);
                Teacher teacher = teacherService.getOne(wrapper3);
                if(teacher!=null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }
                else{
                    return Result.fail().message("原密码输入有误!");
                }
                break;
        }
        return Result.ok();
    }

    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin =adminService.getAdminByToken(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
               Student student =studentService.getStudentByToken(userId);
               map.put("userType",2);
               map.put("user",student);
                break;
            case 3:
                Teacher teacher =teacherService.getTeacherByToken(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionverifiCode =(String) session.getAttribute("verifiCode");
        String verifiCode=loginForm.getVerifiCode();
        if("".equals(sessionverifiCode)||null==sessionverifiCode){
            return Result.fail().message("验证码失效，请重新输入");
        }
        if(!sessionverifiCode.equalsIgnoreCase(verifiCode)){
            return Result.fail().message("验证码输入有误，请重新输入");
        }
        //从session中删除验证码
        session.removeAttribute("verifiCode");
        //按用户类型校验
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                Admin admin =adminService.login(loginForm);
                try {
                    if(null!=admin){
                        String token= JwtHelper.createToken((long) admin.getId(),1);
                        map.put("token",token);
                    }
                    else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                Student student = studentService.login(loginForm);
                try {
                    if(null!=student){
                        String token= JwtHelper.createToken((long) student.getId(),1);
                        map.put("token",token);
                    }
                    else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                Teacher teacher = teacherService.login(loginForm);
                try {
                    if(null!=teacher){
                        String token= JwtHelper.createToken((long) teacher.getId(),1);
                        map.put("token",token);
                    }
                    else{
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码字符串
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

        /*将验证码放入当前请求域*/
        request.getSession().setAttribute("verifiCode",verifiCode);
        try {
            //将验证码图片通过输出流做出响应
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @RequestPart("multipartFile")MultipartFile multipartFile,
            HttpServletRequest request
    ){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(index);
        String newFilename=uuid+substring;
        String portraitPath="\\usr\\java\\image"+newFilename;
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headImg="/upload"+newFilename;
        return Result.ok(headImg);
    }
}
