package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/student")
public class AdminStudentController {
    @Autowired
    StudentServiceImpl studentService;

    @GetMapping("/list/all")
    @ResponseBody
    PageJson<Student> listAllStudent(@SessionAttribute("role")String role){
        PageJson pageJson = new PageJson();
        List<Student> studentList =  studentService.listAll();
        pageJson.setCode(0);
        pageJson.setMsg("success");
        pageJson.setCount(studentList.size());
        pageJson.setData(studentList);
        return pageJson;
    }
    @GetMapping("/info")
    @ResponseBody
    PageJson<Student> getById(String studentId){
        try {
            Student student = studentService.findById(studentId);
            PageJson<Student> studentPageJson = new PageJson<>();
            studentPageJson.setCode(0);
            studentPageJson.setMsg("Success");
            studentPageJson.getData().add(student);
            studentPageJson.setCount(1);
            return studentPageJson;
        }catch (Exception e){
            return null;
        }
    }
    @PostMapping("/update")
    @ResponseBody
    JsonResult update(@RequestBody Student student){

        try {
            studentService.saveOrUpdate(student);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }

        return JsonResult.ok();
    }
    @PostMapping("/delete")
    @ResponseBody
    JsonResult delete(@RequestParam String studentId){
        try {
            studentService.deleteById(studentId);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
