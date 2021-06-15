package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Teacher;
import cn.edu.ecnu.projectmanager.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/teacher")
public class AdminTeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/info")
    @ResponseBody
    public PageJson<Teacher> info(String teacherId){
        Teacher teacher = teacherService.findById(teacherId);
        PageJson<Teacher> pageJson = new PageJson<>();
        if(teacher != null){
            pageJson.setCount(1);
            pageJson.setMsg("Success");
            pageJson.setCode(0);
            pageJson.getData().add(teacher);
        }
        return pageJson;
    }
    @PostMapping("/add")
    @ResponseBody
    public JsonResult add(@RequestBody Teacher teacher){
        try {
            teacherService.add(teacher);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam String teacher_id){
        try {
            teacherService.deleteById(teacher_id);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestBody Teacher teacher){

        try {
            teacherService.saveOrUpdate(teacher);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
