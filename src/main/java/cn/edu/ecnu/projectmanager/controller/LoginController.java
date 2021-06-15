package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.entity.Admin;
import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Teacher;
import cn.edu.ecnu.projectmanager.service.impl.AdminServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.ExpertServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.StudentServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private ExpertServiceImpl expertService;

    /**
     * 登录处理
     * @param userId
     * @param password
     * @param role
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@RequestParam String userId,@RequestParam String password,
                            @RequestParam String role, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            session.setAttribute("role", role);
            log.info("userId:{} password:{} type:{}", userId, password, role);
            role = role.trim();
            // TODO:日后有机会要重构四个角色的实现, 应当使用一个通用user然后进行继承
            if(role.equals("admin")){
                Admin login = adminService.login(userId, password);
                session.setAttribute("user", login);
            }else if(role.equals("student")){
                Student login = studentService.login(userId, password);
                session.setAttribute("user", login);
            }else if(role.equals("teacher")){
                Teacher login = teacherService.login(userId, password);
                session.setAttribute("user", login);
            }else if(role.equals("expert")){
                Expert login = expertService.login(userId, password);
                session.setAttribute("user", login);
            }else{
                return JsonResult.fail("角色错误");
            }
            return  JsonResult.ok("登录成功");
        }catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public JsonResult logout(HttpSession session){
        log.info("out");
        session.invalidate();
        return JsonResult.ok("注销成功");
    }

    @PostMapping("/register")
    @ResponseBody
    public JsonResult register(@RequestParam String userId, @RequestParam String password){
        Student student = new Student();
        student.setPassword(password);
        student.setStu_id(userId);
        try {
            studentService.add(student);
            log.info(student.toString());
            return JsonResult.ok("注册成功");
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
    }
}
