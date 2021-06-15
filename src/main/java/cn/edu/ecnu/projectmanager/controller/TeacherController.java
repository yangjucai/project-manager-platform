package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Teacher;
import cn.edu.ecnu.projectmanager.service.impl.ProjectServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServiceImpl teacherService;
    @Autowired
    ProjectServiceImpl projectService;

    @GetMapping("/listall")
    @ResponseBody
    public PageJson<Teacher> listAll(){
        List<Teacher> teacherList = teacherService.listAll();
        PageJson<Teacher> page = new PageJson<>();
        page.setData(teacherList);
        page.setCount(teacherList.size());
        page.setCode(0);
        page.setMsg("Success");
        return page;
    }
    @GetMapping("/info")
    @ResponseBody
    public PageJson<Teacher> info(@SessionAttribute("user")Teacher teacher){
        return getInfoById(teacher.getTea_id());
    }
    @GetMapping("/info/getById")
    @ResponseBody
    public PageJson<Teacher> getInfoById(String id){
        Teacher teacher = teacherService.findById(id);
        PageJson<Teacher> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setCount(1);
        page.getData().add(teacher);
        return page;
    }
    @PostMapping("/info/update")
    @ResponseBody
    public JsonResult updatePersonalInfo(@RequestBody Teacher teacher, @SessionAttribute("user") Teacher teacherOld){
        try {
            teacherOld.setTea_name(teacher.getTea_name());
            teacherOld.setPassword(teacher.getPassword());
            teacherOld.setPhone(teacher.getPhone());
            teacherOld.setSex(teacher.getSex());
            teacherService.saveOrUpdate(teacherOld);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/project/confirm")
    @ResponseBody
    public JsonResult confirm(@RequestParam("pro_id") Integer pro_id, @RequestParam("tea_comment") String tea_comment, @RequestParam("confirm") Boolean confirm){
        Project project = projectService.findById(pro_id);
        if(project == null){
            return JsonResult.fail("项目不存在");
        }
        if(project.getStatus().trim().equals("等待指导导师确认") && confirm){
            project.setStatus("等待项目审核与立项");
        }
        try {
            project.setTea_comment(tea_comment);
            projectService.saveOrUpdate(project);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @GetMapping("/project/list")
    @ResponseBody
    public PageJson<Project> listProject(@SessionAttribute("user") Teacher teacher){
        List<Project> projectList = teacherService.getProjectList(teacher.getTea_id());
        PageJson<Project> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setData(projectList);
        page.setCount(projectList.size());
        return page;
    }
}
